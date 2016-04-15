package service;

import entity.ID;
import mapper.IDMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class IDServiceImpl implements IDService {

    @Inject
    private IDMapper idMapper;

    /**
     * 由userId 查询出一条用户信息
     * @param userId 用户id
     * @return ID
     */
    @Override
    public ID getID(Integer userId) {
        return idMapper.getID(userId);
    }

    /**
     * 查询所有的用户信息
     * @return list of ID
     */
    @Override
    public List<ID> getAllID() {
        return idMapper.getAllID();
    }

    /**
     * 查询出一页用户信息
     * @param id ID
     * @return list of ID
     */
    @Override
    public List<ID> getIDPage(ID id) {
        return idMapper.getIDPage(id);
    }

    /**
     * 通过用户手机号码查找用户     Added by Tiffany Zhu 2016.03.04
     * @param phoneNum
     * @return
     */
    @Override
    public ID getIDByPhoneNum(String phoneNum) {
        return idMapper.getIDByPhoneNum(phoneNum);
    }
}
