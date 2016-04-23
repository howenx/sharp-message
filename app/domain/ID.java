package domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.sql.Date;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class ID {

    private Integer userId;     //用户ID
    private String nickname;      //用户名
    private String passwd;         //密码
    private String phoneNum;       //电话号码
    private String gender;         //性别
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;         //生日
    private String photoUrl;       //头像
    private String realName;       //真实姓名
    private String cardType;    //证件类型(默认身份证)
    private String cardNum;     //证件号码
    private String cardImg;     //证件图片(A:正面 B:反面)
    private String regIp;       //注册IP
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp regDt;    //注册时间
    private String orReal;      //是否实名认证(默认:N)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastloginDt;//最后登录时间
    private String lastloginIp;   //最后登录IP
    private String status;         //状态(Y:正常,N:阻止)
    private String idType;          //注册用户类型
    private String openId;          //微信或者QQ平台用户唯一ID
    private String idArea;          //用户所在地
    private Long loginTimes;        //登录次数
    private String email;          //邮箱
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp alterDt;  //修改时间
    private String orActive;    //是否激活(默认:N)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastpwdchgDt;//最后修改密码时间
    private String desc;           //备注

    //分页,每页多少条
    private Integer pageSize;
    //分页,从第几条开始
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;
    //查询开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp startAt;
    //查询结束时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp endAt;

    public ID() {
    }

    public ID(Integer userId, String nickname, String passwd, String phoneNum, String gender, Date birthday, String photoUrl, String realName, String cardType, String cardNum, String cardImg, String regIp, Timestamp regDt, String orReal, Timestamp lastloginDt, String lastloginIp, String status, String idType, String openId, String idArea, Long loginTimes, String email, Timestamp alterDt, String orActive, Timestamp lastpwdchgDt, String desc, Integer pageSize, Integer offset, String sort, String order, Timestamp startAt, Timestamp endAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.passwd = passwd;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.birthday = birthday;
        this.photoUrl = photoUrl;
        this.realName = realName;
        this.cardType = cardType;
        this.cardNum = cardNum;
        this.cardImg = cardImg;
        this.regIp = regIp;
        this.regDt = regDt;
        this.orReal = orReal;
        this.lastloginDt = lastloginDt;
        this.lastloginIp = lastloginIp;
        this.status = status;
        this.idType = idType;
        this.openId = openId;
        this.idArea = idArea;
        this.loginTimes = loginTimes;
        this.email = email;
        this.alterDt = alterDt;
        this.orActive = orActive;
        this.lastpwdchgDt = lastpwdchgDt;
        this.desc = desc;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Timestamp getRegDt() {
        return regDt;
    }

    public void setRegDt(Timestamp regDt) {
        this.regDt = regDt;
    }

    public String getOrReal() {
        return orReal;
    }

    public void setOrReal(String orReal) {
        this.orReal = orReal;
    }

    public Timestamp getLastloginDt() {
        return lastloginDt;
    }

    public void setLastloginDt(Timestamp lastloginDt) {
        this.lastloginDt = lastloginDt;
    }

    public String getLastloginIp() {
        return lastloginIp;
    }

    public void setLastloginIp(String lastloginIp) {
        this.lastloginIp = lastloginIp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public Long getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Long loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getAlterDt() {
        return alterDt;
    }

    public void setAlterDt(Timestamp alterDt) {
        this.alterDt = alterDt;
    }

    public String getOrActive() {
        return orActive;
    }

    public void setOrActive(String orActive) {
        this.orActive = orActive;
    }

    public Timestamp getLastpwdchgDt() {
        return lastpwdchgDt;
    }

    public void setLastpwdchgDt(Timestamp lastpwdchgDt) {
        this.lastpwdchgDt = lastpwdchgDt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    @Override
    public String toString() {
        return "ID{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", passwd='" + passwd + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", photoUrl='" + photoUrl + '\'' +
                ", realName='" + realName + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", cardImg='" + cardImg + '\'' +
                ", regIp='" + regIp + '\'' +
                ", regDt=" + regDt +
                ", orReal='" + orReal + '\'' +
                ", lastloginDt=" + lastloginDt +
                ", lastloginIp='" + lastloginIp + '\'' +
                ", status='" + status + '\'' +
                ", idType='" + idType + '\'' +
                ", openId='" + openId + '\'' +
                ", idArea='" + idArea + '\'' +
                ", loginTimes=" + loginTimes +
                ", email='" + email + '\'' +
                ", alterDt=" + alterDt +
                ", orActive='" + orActive + '\'' +
                ", lastpwdchgDt=" + lastpwdchgDt +
                ", desc='" + desc + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                '}';
    }
}