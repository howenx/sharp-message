package mapper;

import domain.Item;
import domain.VersionVo;

import java.util.List;

/**
 * 商品管理mapper
 * Created by howen on 15/11/11.
 */
public interface ItemMapper {

    List<Item> getItemPage(Item item);

    Long itemInsert(Item item);

    Item getItem(Long id);

    void itemUpdate(Item item);

    List<Item> getItemsAll();

    List<VersionVo> getVersioning(VersionVo versionVo);

}
