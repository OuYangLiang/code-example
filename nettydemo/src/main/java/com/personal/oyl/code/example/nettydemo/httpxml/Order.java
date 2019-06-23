package com.personal.oyl.code.example.nettydemo.httpxml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class Order {
    private String customer;
    private String billTo;
    private Float total;


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }


    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        Order order = Order.create();
        System.out.println(order.toXml());

        Order order1 = Order.parseFrom(order.toXml());
        System.out.println(order1.toXml());
    }

    public static Order parseFrom(String xml) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document doc;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes())) {
            doc = reader.read(bais);
        }
        Element root = doc.getRootElement();
        Order order = new Order();
        order.setCustomer(root.element("customer").getText());
        order.setBillTo(root.element("billto").getText());
        order.setTotal(Float.parseFloat(root.element("total").getText()));
        return order;
    }

    public static Order create() {
        Order order = new Order();
        order.setCustomer("OuYang Liang");
        order.setBillTo("Nan Jing");
        order.setTotal(100F);

        return order;
    }

    public String toXml() throws IOException {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("order");
        Element customer = root.addElement("customer");
        customer.setText(this.getCustomer());
        Element billTo = root.addElement("billto");
        billTo.setText(this.getBillTo());
        Element total = root.addElement("total");
        total.setText(this.getTotal().toString());

        try(StringWriter sw = new StringWriter()) {
            doc.write(sw);
            return sw.toString();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer='" + customer + '\'' +
                ", billTo='" + billTo + '\'' +
                ", total=" + total +
                '}';
    }
}
