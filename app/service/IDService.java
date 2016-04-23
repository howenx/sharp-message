package service;

import domain.ID;

import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public interface IDService {

    /**
     * 由userId 查询出一条用户信息
     * @param userId 用户id
     * @return ID
     */
    ID getID(Integer userId);

    /**
     * 查询所有的用户信息
     * @return list of ID
     */
    List<ID> getAllID();

    /**
     * 查询出一页用户信息
     * @param id ID
     * @return list of ID
     */
    List<ID> getIDPage(ID id);

    /**
     * 通过用户手机号码查找用户     Added by Tiffany Zhu 2016.03.04
     * @param phoneNum
     * @return
     */
    ID getIDByPhoneNum(String phoneNum);

}
