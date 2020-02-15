package com.igurukul.cms.service;


import com.igurukul.cms.dao.CustomerDAO;
import com.igurukul.cms.exception.CustomerNotFoundException;
import com.igurukul.cms.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class CustomerService {
    @Autowired
    private CustomerDAO customerDAO;

    private int customerIdCount=1;
    private List<Customer> customerList =new CopyOnWriteArrayList<>();

    public Customer addCustomer(Customer customer) {
        //Without DB
        /*customer.setCustomerId(customerIdCount);
        customerList.add(customer);
        customerIdCount++;
        return  customer;*/

        //With DB
        return customerDAO.save(customer);
    }

    public List<Customer> getCustomers() {
        //Without DB
        //return customerList;

        //With DB
        return customerDAO.findAll();
    }

    public Customer getCustomer(int customerId) {
        //Without DB
        /*return customerList
                .stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst()
                .get();*/
        //With DB
        Optional<Customer> optionalCustomer = customerDAO.findById(customerId);

        if(!optionalCustomer.isPresent())
            throw new CustomerNotFoundException("Customer Record is not available ...");

        return optionalCustomer
                .get();
    }

    public Customer updateCustomer(int customerId, Customer customer) {
        //Without DB
        /*        customerList
                .stream()
                .forEach(c -> {
                    if(c.getCustomerId() == customerId) {
                        c.setCustomerFirstName(customer.getCustomerFirstName());
                        c.setCustomerLastName(customer.getCustomerLastName());
                        c.setCustomerEmail(customer.getCustomerEmail());
                    }
                });

        return customerList
                .stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst()
                .get();*/

    //With DB
        customer.setCustomerId(customerId);
        return customerDAO.save(customer);
    }

    public void deleteCustomer(int customerId) {
        //Without DB
        /*customerList
                .stream()
                .forEach(c -> {
                    if(c.getCustomerId() == customerId) {
                        customerList.remove(c);
                    }
                });*/

        //With DB
        customerDAO.deleteById(customerId);
    }
}
