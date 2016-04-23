package service;

import domain.Item;
import domain.VersionVo;
import mapper.InventoryMapper;
import mapper.ItemMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * 商品管理接口
 * Created by howen on 15/11/11.
 */
public class ItemServiceImpl implements ItemService{

    @Inject
    private ItemMapper itemMapper;

    @Inject
    private InventoryMapper inventoryMapper;

    /**
     * 分页查询商品列表
     * @param item
     * @return list of Item
     */
    @Override
    public List<Item> itemSearch(Item item) {
        return itemMapper.getItemPage(item);
    }

    @Override
    public Long itemInsert(Item item) {
        return itemMapper.itemInsert(item);
    }

    @Override
    public void itemUpdate(Item item) {
        itemMapper.itemUpdate(item);
    }

    /**
     * 由商品id得到商品Item
     * @param id 商品id
     * @return Item
     */
    @Override
    public Item getItem(Long id) {
        return itemMapper.getItem(id);
    }

    /**
     * added by Tiffany Zhu 15/11/27.
     * 商品查询
     * @return list
     */
    @Override
    public List<Item> getItemsAll() { return itemMapper.getItemsAll(); }

    @Override
    public List<VersionVo> getVersioning(VersionVo versionVo) {
        return itemMapper.getVersioning(versionVo);
    }

}
