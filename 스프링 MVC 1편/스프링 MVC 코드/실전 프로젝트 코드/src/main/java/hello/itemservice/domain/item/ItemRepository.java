package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {

    // 멀티 스레드 환경에서는 ConcurrentHashMap 사용해야 한다!
    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;
    
    // store에 아 이템 추가
    public Item save(Item item) {
        item.setId(++sequence);

        store.put(sequence, item);

        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        // Map 에있는 값 리스트에 부어넣을때 사용하는것 .values();
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {

        Item findItem = findById(itemId);

        if (findItem != null) {
            findItem.setItemName(updateParam.getItemName());
            findItem.setPrice(updateParam.getPrice());
            findItem.setQuantity(updateParam.getQuantity());
        }
        store.put(itemId, findItem);
    }

    // .clear() 메서드 사용
    public void clearStore() {
        store.clear();;
    }

}
