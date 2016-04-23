package service;

import domain.Item;
import domain.VersionVo;

import java.util.List;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
public interface ItemService {

    List<Item> itemSearch(Item item);

    Long itemInsert(Item item);

    void itemUpdate(Item item);

    Item getItem(Long id);

    List<Item> getItemsAll();

    List<VersionVo> getVersioning(VersionVo versionVo);


}
