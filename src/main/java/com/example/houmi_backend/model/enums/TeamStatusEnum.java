package com.example.houmi_backend.model.enums;

public enum TeamStatusEnum {
    /**
     * 队伍状态
     */
    PUBLIC_STATUS("公开",0),
    PRIVATE_STATUS("私密",1),
    ENCRYPTION_STATUS("加密",2);




    private  String statusName;

    private  Integer statusId;

    TeamStatusEnum(String statusName,Integer statusId){
        this.statusId=statusId;
        this.statusName=statusName;
    }

    public  String getStatusNameById(Integer statusId)
    {
        TeamStatusEnum[] statusEnums=TeamStatusEnum.values();
        //遍历三个枚举对象只要他们的枚举值等于传入状态就返回对应的状态名
        for (TeamStatusEnum statusEnum:statusEnums)
        {
            if (statusEnum.getStatusId()==statusId)
            {
                return statusEnum.getStatusName();
            }
        }
        return null;
    }

    public String getStatusName()
    {
        return statusName;
    }
    public Integer getStatusId()
    {
        return statusId;
    }


}
