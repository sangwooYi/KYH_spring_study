package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 전체 조회
     * @param model
     * @return
     */
    @GetMapping("")
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    /**
     *
     * 상품 등록페이지
     *
     * @return
     */
    @GetMapping("/add")
    public String addForm() {

        return "basic/addForm";
    }

    /**
     *
     * 상품 등록처리
     *
     * 여기서도 @ModelAttribute 로 받을 수 있다.
     * 당연한건 여기서 매핑되서 준 item 파라미터에는 id 값은 null 상태! ( 당연한거 )
     * @ModelAttribute("item") Item item 이렇게 @ModelAttribute에 () 로 이름을 정의하면
     * model.addAttribute("item", item) 이부분을 생략해도 된다.
     * ( @ModelAttribute("명칭") 이렇게 선언하면
     * model.addAttribute("명칭", 객체) 이부분을 알아서 해준다 . )
     *
     * @return
     */
    //@PostMapping("/add")
    public String addForm(@ModelAttribute Item item,
                          Model model) {

        Item nItem = new Item(item.getItemName(), item.getPrice(), item.getQuantity());
        itemRepository.save(nItem);

        model.addAttribute("item", nItem);

        return "basic/item";
    }

    /**
     * 이렇게 HTML 폼으로 전달하는 경우에는 @ModelAttribute 이용해서 간단하게 코드 작성 가능
     * @param item
     * @param model
     * @return
     */
    @PostMapping("/add")
    public String addFormV2(@ModelAttribute("item") Item item,
                          Model model) {

        itemRepository.save(item);

        return "basic/item";
    }


    /**
     * 특정 상품 조회
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * 상품 수정 페이지
     * @param itemId
     * @return
     */
    @GetMapping("/{itemId}/edit")
    public ModelAndView editForm(@PathVariable Long itemId) {

        Item item = itemRepository.findById(itemId);

        // 논리적 View 경로를 통해 전체 Path 를 알아서 찾아줌
        ModelAndView mv = new ModelAndView("basic/editForm");
        mv.addObject("item", item);

        return mv;
    }

    /**
     * 상품 수정
     * 단순히 String 말고 이렇게 ModelAndView로 반환하는것도 가능 ( 이게 더 사실 자주 쓰던 방식 )
     * 
     * @param itemId
     * @param item
     * @return
     */
    @PostMapping("/{itemId}/edit")
    public ModelAndView editItem(@PathVariable Long itemId,
                                 @ModelAttribute Item item) {
    
        itemRepository.update(itemId, item);

        // redirect도 동일하게 이렇게 하면 됨!
        ModelAndView mv = new ModelAndView("redirect:/basic/items/{itemId}");

        return mv;
    }


    // 테스트용 데이터
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("물품 A", 10000, 1000));
        itemRepository.save(new Item("물품 B", 50000, 100));
        itemRepository.save(new Item("물품 C", 1000, 10000));
    }

    
}
