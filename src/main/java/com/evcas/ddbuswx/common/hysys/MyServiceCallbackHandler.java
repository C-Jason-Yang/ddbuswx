package com.evcas.ddbuswx.common.hysys;


/**
 *  MyServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class MyServiceCallbackHandler {
    protected Object clientData;

    /**
     * UserController can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public MyServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public MyServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for selectLineDetail method
     * override this method for handling normal response from selectLineDetail operation
     */
    public void receiveResultselectLineDetail(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.SelectLineDetailResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from selectLineDetail operation
     */
    public void receiveErrorselectLineDetail(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getRealNameConsumeInfo method
     * override this method for handling normal response from getRealNameConsumeInfo operation
     */
    public void receiveResultgetRealNameConsumeInfo(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetRealNameConsumeInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getRealNameConsumeInfo operation
     */
    public void receiveErrorgetRealNameConsumeInfo(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for driverFirstLastList method
     * override this method for handling normal response from driverFirstLastList operation
     */
    public void receiveResultdriverFirstLastList(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.DriverFirstLastListResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from driverFirstLastList operation
     */
    public void receiveErrordriverFirstLastList(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getScheduleData method
     * override this method for handling normal response from getScheduleData operation
     */
    public void receiveResultgetScheduleData(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetScheduleDataResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getScheduleData operation
     */
    public void receiveErrorgetScheduleData(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getBusMileList method
     * override this method for handling normal response from getBusMileList operation
     */
    public void receiveResultgetBusMileList(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetBusMileListResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getBusMileList operation
     */
    public void receiveErrorgetBusMileList(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for selectDepartment method
     * override this method for handling normal response from selectDepartment operation
     */
    public void receiveResultselectDepartment(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.SelectDepartmentResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from selectDepartment operation
     */
    public void receiveErrorselectDepartment(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getConsumeInfoByCard method
     * override this method for handling normal response from getConsumeInfoByCard operation
     */
    public void receiveResultgetConsumeInfoByCard(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetConsumeInfoByCardResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getConsumeInfoByCard operation
     */
    public void receiveErrorgetConsumeInfoByCard(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getMileList method
     * override this method for handling normal response from getMileList operation
     */
    public void receiveResultgetMileList(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetMileListResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getMileList operation
     */
    public void receiveErrorgetMileList(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for isShow method
     * override this method for handling normal response from isShow operation
     */
    public void receiveResultisShow(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.IsShowResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from isShow operation
     */
    public void receiveErrorisShow(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getOverSpeedInfo method
     * override this method for handling normal response from getOverSpeedInfo operation
     */
    public void receiveResultgetOverSpeedInfo(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetOverSpeedInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getOverSpeedInfo operation
     */
    public void receiveErrorgetOverSpeedInfo(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getPlanRealList method
     * override this method for handling normal response from getPlanRealList operation
     */
    public void receiveResultgetPlanRealList(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetPlanRealListResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getPlanRealList operation
     */
    public void receiveErrorgetPlanRealList(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDriverGpsMile method
     * override this method for handling normal response from getDriverGpsMile operation
     */
    public void receiveResultgetDriverGpsMile(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetDriverGpsMileResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDriverGpsMile operation
     */
    public void receiveErrorgetDriverGpsMile(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for selectStation method
     * override this method for handling normal response from selectStation operation
     */
    public void receiveResultselectStation(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.SelectStationResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from selectStation operation
     */
    public void receiveErrorselectStation(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getFuelList method
     * override this method for handling normal response from getFuelList operation
     */
    public void receiveResultgetFuelList(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetFuelListResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getFuelList operation
     */
    public void receiveErrorgetFuelList(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for selectCarInfo method
     * override this method for handling normal response from selectCarInfo operation
     */
    public void receiveResultselectCarInfo(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.SelectCarInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from selectCarInfo operation
     */
    public void receiveErrorselectCarInfo(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getZDLAll method
     * override this method for handling normal response from getZDLAll operation
     */
    public void receiveResultgetZDLAll(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetZDLAllResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getZDLAll operation
     */
    public void receiveErrorgetZDLAll(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDriverWorkTime method
     * override this method for handling normal response from getDriverWorkTime operation
     */
    public void receiveResultgetDriverWorkTime(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetDriverWorkTimeResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDriverWorkTime operation
     */
    public void receiveErrorgetDriverWorkTime(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getConsumeInfoByDate method
     * override this method for handling normal response from getConsumeInfoByDate operation
     */
    public void receiveResultgetConsumeInfoByDate(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetConsumeInfoByDateResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getConsumeInfoByDate operation
     */
    public void receiveErrorgetConsumeInfoByDate(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getBusTimeList method
     * override this method for handling normal response from getBusTimeList operation
     */
    public void receiveResultgetBusTimeList(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetBusTimeListResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getBusTimeList operation
     */
    public void receiveErrorgetBusTimeList(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getLineFirstLast method
     * override this method for handling normal response from getLineFirstLast operation
     */
    public void receiveResultgetLineFirstLast(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetLineFirstLastResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getLineFirstLast operation
     */
    public void receiveErrorgetLineFirstLast(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getBusDriverTrips method
     * override this method for handling normal response from getBusDriverTrips operation
     */
    public void receiveResultgetBusDriverTrips(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetBusDriverTripsResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getBusDriverTrips operation
     */
    public void receiveErrorgetBusDriverTrips(java.lang.Exception e) {
    }

    // No methods generated for meps other than in-out

    /**
     * auto generated Axis2 call back method for getDriverInfo method
     * override this method for handling normal response from getDriverInfo operation
     */
    public void receiveResultgetDriverInfo(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetDriverInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDriverInfo operation
     */
    public void receiveErrorgetDriverInfo(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getConsumeRecord method
     * override this method for handling normal response from getConsumeRecord operation
     */
    public void receiveResultgetConsumeRecord(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetConsumeRecordResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getConsumeRecord operation
     */
    public void receiveErrorgetConsumeRecord(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getBusGpsMile method
     * override this method for handling normal response from getBusGpsMile operation
     */
    public void receiveResultgetBusGpsMile(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.GetBusGpsMileResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getBusGpsMile operation
     */
    public void receiveErrorgetBusGpsMile(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for selectFirstLast method
     * override this method for handling normal response from selectFirstLast operation
     */
    public void receiveResultselectFirstLast(
            com.evcas.ddbuswx.common.hysys.MyServiceStub.SelectFirstLastResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from selectFirstLast operation
     */
    public void receiveErrorselectFirstLast(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for selectLineInfo method
     * override this method for handling normal response from selectLineInfo operation
     */
    public void receiveResultselectLineInfo(com.evcas.ddbuswx.common.hysys.MyServiceStub.SelectLineInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from selectLineInfo operation
     */
    public void receiveErrorselectLineInfo(java.lang.Exception e) {
    }
}
