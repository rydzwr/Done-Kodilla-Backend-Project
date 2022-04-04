import com.kodilla.ecommercee.controller.exceptions.CartNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.OrderDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderDbService orderDbService;

    @Autowired
    public OrderController(OrderMapper orderMapper, OrderDbService orderDbService) {
        this.orderDbService = orderDbService;
        this.orderMapper = orderMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderMapper.mapToOrderDtoList(orderDbService.getAllOrders());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        OrderDto orderDto = orderMapper.mapToOrderDto(orderDbService.getOrder(orderId));
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addOrder(@RequestBody OrderDto newOrderDto) throws CartNotFoundException {
        orderDbService.saveOrder(orderMapper.mapToOrder(newOrderDto));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{orderId}/add_product/{productId}")
    public ResponseEntity<Void> addProduct(@PathVariable Long orderId,
                                           @PathVariable Long productId) throws ProductNotFoundException {
        orderDbService.addProductToOrder(orderId, productId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{orderId}/remove_product/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long orderId,
                                              @PathVariable Long productId) throws ProductNotFoundException {
        orderDbService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) throws CartNotFoundException {
        orderDbService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}