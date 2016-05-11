package service;

import domain.WeiShengExpress;

/**
 * Created by Sunny Wu on 16/5/11.
 * kakao china.
 */
public interface WeiShengExpressService {

    /**
     * 获取一条威盛快递单
     * @return WeiShengExpress
     */
    WeiShengExpress getExpress();

    /**
     * 快递单使用后状态置为已使用
     * @param weiShengExpress
     */
    void useExpress(WeiShengExpress weiShengExpress);
}
