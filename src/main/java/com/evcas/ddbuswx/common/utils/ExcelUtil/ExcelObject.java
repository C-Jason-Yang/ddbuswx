package com.evcas.ddbuswx.common.utils.ExcelUtil;

import com.evcas.ddbuswx.common.utils.ExcelUtil.annotation.ExcelImportField;
import com.evcas.ddbuswx.common.utils.ExcelUtil.constant.AutoCreateTypeEnum;
import com.evcas.ddbuswx.common.utils.ReflectUtil;
import com.evcas.ddbuswx.common.utils.RegularUtil;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel工具类
 * Created by noxn on 2018/9/18.
 */
public class ExcelObject {

    private HSSFWorkbook workbook = null;

    public Integer currentImportSheetLineNum;

    public ExcelObject(String filePath) {
        this.workbook = new HSSFWorkbook();
        File file = new File(filePath);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //读取Excel文件流中的数据到workbook
            workbook = (HSSFWorkbook) WorkbookFactory.create(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建Excel表单
     * @param sheetName
     */
    public void createSheet(String sheetName) {
        workbook.createSheet(sheetName);
    }

    /**
     * 获取Excel文件流中的数据
     * @param tClass
     * @param sheetNum
     * @param dataStartRowNum
     * @param <T>
     * @return
     */
    public <T> List<T> getFileDataList(Class<T> tClass, Integer sheetNum, Integer dataStartRowNum) {
        List<T> tList = new ArrayList<>();
        //获取指定的Excel表单
        HSSFSheet sheet = workbook.getSheetAt(sheetNum);
        //获取Excel表单中 最后一行数据的行号
        Integer dataEndLRowNum = sheet.getLastRowNum();
        currentImportSheetLineNum = dataEndLRowNum + 1;
        //自有成员变量
        Field[] ownMemberVariableFields = tClass.getDeclaredFields();
        //继承对象成员变量
        Field[] parentClassMemberVariableFields = tClass.getFields();
        if (ownMemberVariableFields.length == 0 && parentClassMemberVariableFields.length == 0) {
            //TODO 抛出导入异常，导入数据对象上没有成员变量
        }
        List<Map<String, Object>> annotationMapList = new ArrayList<>();
        //循环自有成员变量获取成员变量上的ExcelImportField注解
        annotationMapList = getObjectExcelImportFieldAnnotationList(annotationMapList, ownMemberVariableFields);
        //循环超类的成员变量获取成员变量上的ExcelImportField注解
        annotationMapList = getObjectExcelImportFieldAnnotationList(annotationMapList, parentClassMemberVariableFields);
        Collections.sort(annotationMapList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer sort1 = Integer.valueOf(o1.get("sort").toString()) ;
                Integer sort2 = Integer.valueOf(o2.get("sort").toString()) ; //name1是从你list里面拿出来的第二个name
                return sort1.compareTo(sort2);
            }
        });
        //跳过表头，循环Excel表单集合
        line:
        for (int i = dataStartRowNum; i <= dataEndLRowNum; i++) {
            //逐行获取
            HSSFRow row = sheet.getRow(i);
            //声明泛型数据对象
            T t = null;
            try {
                try {
                    t = tClass.newInstance();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for (int a = 0; a < annotationMapList.size(); a++) {
                Object[] fieldInfo = (Object[]) annotationMapList.get(a).get("field");
                ExcelImportField excelImportField = (ExcelImportField) fieldInfo[0];
                Field field = (Field) fieldInfo[1];
                Method method = ReflectUtil.obtainSetMethod(field, tClass);
                //判断成员变量数据是不是自动创建
                if (excelImportField.autoCreate()) {
                    //判断创建类型
                    if (excelImportField.autoCreateType().equals(AutoCreateTypeEnum.UUID)) {
                        ReflectUtil.setFieldValue(t, method, UuidUtil.getUuid());
                    } else if (excelImportField.autoCreateType().equals(AutoCreateTypeEnum.CURRENTTIME)) {
                        //TODO 添加当前时间
                    }
                } else {
                    if (row.getCell(excelImportField.sort()) != null) {
                        if (!row.getCell(excelImportField.sort()).getCellType().name().equals(excelImportField.cellValueType())) {
                            continue line;
                        }
                        if (CellType.STRING.name().equals(row.getCell(excelImportField.sort()).getCellType().name())) {
                            String value = row.getCell(excelImportField.sort()).getStringCellValue();
                            if (value.trim().equals("")) {
                                continue line;
                            }
                            //如果需要验证Excel数据
                            if (excelImportField.validate()) {
                                String regx = excelImportField.regex();
                                boolean validateResult = RegularUtil.validate(regx, value);
                                if (!validateResult) {
                                    continue line;
                                }
                            }
                            ReflectUtil.setFieldValue(t, method, value);
                        } else if (CellType.NUMERIC.name().equals(row.getCell(excelImportField.sort()).getCellType().name())) {
                            //如果需要验证Excel数据
                            if (excelImportField.validate()) {
                                String regx = excelImportField.regex();
                                double value = row.getCell(excelImportField.sort()).getNumericCellValue();
                                boolean validateResult = RegularUtil.validate(regx, String.valueOf(value));
                                if (!validateResult) {
                                    continue line;
                                }
                            }
                            ReflectUtil.setFieldValue(t, method, row.getCell(excelImportField.sort()).getNumericCellValue());
                        } else {
                            System.err.println(row.getCell(excelImportField.sort()).getCellType());
                            System.err.println(String.valueOf(row.getCell(excelImportField.sort()).getNumericCellValue()));
                            //TODO 其他Excel数据格式类型
                            continue line;
                        }
                    } else {
                        continue line;
                    }
                }
            }
            tList.add(t);
        }
        return tList;
    }

    /**
     * 返回Excel文件流
     * @param req
     * @param fileName
     */
    public void exportExcelFileStream(HttpServletResponse req, String fileName) {
        try {
            req.setContentType("application/octet-stream;charset=ISO8859-1");
            req.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            req.addHeader("Pargam", "no-cache");
            req.addHeader("Cache-Control", "no-cache");
            OutputStream os = req.getOutputStream();
            this.workbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Field数组中有ExcelImportField注解的注解信息和Fidld集合
     * @param annotationMapList
     * @param fieldArr
     * @return
     */
    private List<Map<String, Object>> getObjectExcelImportFieldAnnotationList(List<Map<String, Object>> annotationMapList, Field[] fieldArr) {
        if (annotationMapList == null) {
            annotationMapList = new ArrayList<>();
        }
        for (int i = 0; i < fieldArr.length; i++) {
            Field field = fieldArr[i];
            ExcelImportField excelImportField = field.getDeclaredAnnotation(ExcelImportField.class);
            if (excelImportField != null) {
                Map<String, Object> annotationMap = new HashMap<>();
                annotationMap.put("sort", excelImportField.sort());
                annotationMap.put("field", new Object[] {excelImportField, field});
                annotationMapList.add(annotationMap);
            }
        }
        return annotationMapList;
    }

    public static void main(String[] args) {
        String test = "";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher("123qweQWE");
        if (matcher.matches()) {
            System.err.println("通过");
        } else {
            System.err.println("不通过");
        }
    }
}
