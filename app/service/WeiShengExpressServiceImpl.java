package service;

import domain.WeiShengExpress;
import mapper.WeiShengExpressMapper;

import javax.inject.Inject;

/**
 * Created by Sunny Wu on 16/5/11.
 * kakao china.
 */
public class WeiShengExpressServiceImpl implements WeiShengExpressService {

    @Inject
    WeiShengExpressMapper weiShengExpressMapper;

    /**
     * 获取一条威盛快递单
     * @return WeiShengExpress
     */
    @Override
    public WeiShengExpress getExpress() {
        return weiShengExpressMapper.getExpress();
    }

    /**
     * 快递单使用后状态置为已使用
     * @param weiShengExpress
     */
    @Override
    public void useExpress(WeiShengExpress weiShengExpress) {
        weiShengExpressMapper.useExpress(weiShengExpress);
    }
}
