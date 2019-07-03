<html>
    <head>
        <title>添加红包</title>
    </head>
    <body>
        <form enctype="multipart/form-data"  method="post" action="importData">
            <select name="areaCode">
                <option value="CN000002">太和</option>
                <option value="CN000003">利辛</option>
            </select>

            <input type="file" name="excel" />
            <button type="submit">添加</button>
        </form>
    </body>
</html>