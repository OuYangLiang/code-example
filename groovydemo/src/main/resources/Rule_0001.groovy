import com.personal.oyl.groovydemo.Order
import com.personal.oyl.groovydemo.Predicate

class Rule0001 implements Predicate{
    @Override
    boolean test(Order order) {
        null != order && null != order.getOrderNo() && "002" == order.getOrderNo()
    }
}
$a.doPrint1()
new Rule0001()
