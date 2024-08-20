package com.ohgiraffers.transactional.section01.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    /* 설명. sqlSession.getMapper() 대신 @Mapper가 달려 하위 구현체가 관리되면 의존성 주입 받을 수 있다. */
    private OrderMapper orderMapper;
    private MenuMapper menuMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper, MenuMapper menuMapper) {
        this.orderMapper = orderMapper;
        this.menuMapper = menuMapper;
    }

    /* 설명.
     *  OrderDTO에 담겨 컨트롤러에서 넘어온다는 가정
     *  : Service계층부터 개발할 때는 사용자가 입력한 값들이 어떻게 DTO 또는 Map으로 묶여서 Controller로부터
     *    넘어올 지 충분히 고민한 후 매개변수를 작성하고 개발한다.
     *    현재의 경우(주문 한 건 발생) 사용자가 고른 메뉴를 각각의 코드 번호와 고른 메뉴 갯수, 그리고 서버의 현재시간이
     *    담긴 채로 넘어왔다는 생각을 가지고 개발해 나가자.
    * */
    @Transactional
    public void registNewOrder(OrderDTO orderInfo) {

        /* 설명. 1. 주문한 메뉴 코드 추출(DTO에서) */
        List<Integer> menuCode = orderInfo.getOrderMenus()
                                        .stream()
                                        .map(OrderMenuDTO::getMenuCode)
                                        .collect(Collectors.toList());

//        List<Integer> menuCode = new ArrayList<>();
//        List<OrderMenuDTO> orderMenus = orderInfo.getOrderMenus();
//        for (OrderMenuDTO orderMenu : orderMenus) {
//            menuCode.add(orderMenu.getMenuCode());
//        }
//        menuCode.forEach(System.out::println);

        Map<String, List<Integer>> map = new HashMap<>();
        map.put("menuCodes", menuCode);

        /* 설명. 2. 주문한 메뉴 별 Menu 엔티티에 담아서 조회(Select)해 오기 (부가적인 메뉴의 정보 추출(단가 등)) */
        List<Menu> menus = menuMapper.selectMenuByMenuCodes(map);

        /* 설명. 3. 이 주문 건에 대한 주문 총 합계를 계산(insert 한 번으로 처리하기 위해..) */
        int totalOrderPrice = calcTotalOrderPrice(orderInfo.getOrderMenus(), menus);
        System.out.println("totalOrderPrice = " + totalOrderPrice);

        /* 설명. 4. 1부터 3까지를 하면 tbl_order 테이블에 추가(insert)가 가능 */
        /* 설명. 4-1. insert를 하기 위해 테이블과 매칭되는 Entity 클래스(Order)로 옮겨 담는다.(DTO -> Entity) */

        /* 설명. OrderDTO -> List<OrderMenuDTO> 추출 -> List<OrderMenu> */
        List<OrderMenu> oMenus = new ArrayList<>(
                orderInfo.getOrderMenus().stream()
                        .map(dto -> {
                            return new OrderMenu(dto.getMenuCode(), dto.getOrderAmount());
                        }).collect(Collectors.toList())
        );

//        List<OrderMenu> oMenus = new ArrayList<>();
//        List<OrderMenuDTO> orderMenus = orderInfo.getOrderMenus();
//        for(OrderMenuDTO orderMenu : orderMenus ){
//            oMenus.add(new OrderMenu(orderMenu.getMenuCode(), orderMenu.getOrderAmount()));
//        }

        /* 설명. OrderDTO -> Order */
        Order order = new Order(orderInfo.getOrderDate(), orderInfo.getOrderTime(), totalOrderPrice);

        /* 설명. 4-2. tbl_order 테이블에 insert(insert에 활용된 객체에 selectkey로 조회된 pk값이 담겨 돌아온다.) */
        orderMapper.registOrder(order);
        System.out.println("order insert 후 order에 담긴 pk 값 확인: " + order);

        /* 설명. 5. tbl_order_menu에도 주문한 메뉴 갯수만큼 주문한 메뉴를 추가(insert)한다. */
        int orderMenuSize = oMenus.size();

        /* 설명. 주문한 메뉴들의 orderCode들이 비어 있었으니 주문 insert 후 알게 된 pk를 채워 넣는다. */
        for (int i = 0; i < orderMenuSize; i++) {
            OrderMenu orderMenu = oMenus.get(i);
            orderMenu.setOrderCode(order.getOrderCode());       // entity에 setter 하나 추가

            orderMapper.registOrderMenu(orderMenu);
        }

    }

    /* 설명. 주문건에 대한 총 합계 금액 계산 메소드(orderMenus: 사용자의 주문내용, menus: 조회된 메뉴 전체 내용 */
    private int calcTotalOrderPrice(List<OrderMenuDTO> orderMenus, List<Menu> menus) {
        int totalOrderPrice = 0;

        int orderMenusSize = orderMenus.size();
        for(int i = 0; i < orderMenusSize; i++) {       // 하나의 메뉴에 대한 합계를 총 합계에 누적하는 반복문
            OrderMenuDTO orderMenu = orderMenus.get(i);
            Menu menu = menus.get(i);
            totalOrderPrice += menu.getMenuPrice() * orderMenu.getOrderAmount();

        }

        return totalOrderPrice;
    }

}
