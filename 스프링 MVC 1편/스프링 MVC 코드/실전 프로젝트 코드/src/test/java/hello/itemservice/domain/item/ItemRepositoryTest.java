package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {

        // given
        Item item1 = new Item("물품 A", 10000, 100);

        //when
        Item resItem =  itemRepository.save(item1);

        //then

        // String 값 비교에는 isEqualTo 를 사용하고, 객체의 참조 값(주소)를 비교할 때에는 isSameAs 를 사용한다!
        // 여기선 isSameAs 가 더 적절할 듯?
        Assertions.assertThat(item1).isSameAs(resItem);
    }

    @Test
    void findAll(){
        // given
        Item item1 = new Item("물품 A", 10000, 100);
        Item item2 = new Item("물품 B", 10000, 100);
        Item item3 = new Item("물품 C", 10000, 100);

        //when
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        //then
        List<Item> itemList = itemRepository.findAll();

        Assertions.assertThat(itemList.size()).isEqualTo(3);
        Assertions.assertThat(itemList).contains(item1, item2, item3);
    }

    @Test
    void updateItem() {

        // given
        Item item1 = new Item("물품 A", 10000, 100);
        itemRepository.save(item1);
        Long itemId = item1.getId();

        // when
        Item nItem = new Item("아이묭", 100000, 10);
        itemRepository.update(itemId, nItem);


        // then
        item1 = itemRepository.findById(itemId);
        Assertions.assertThat(item1.getItemName()).isEqualTo(nItem.getItemName());
        Assertions.assertThat(item1.getPrice()).isEqualTo(nItem.getPrice());
    }
}
