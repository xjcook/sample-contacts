package io.xjhub.samplecontacts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

final class Api {
    // Prevents accidentally instantiating this class
    private Api() {}

    static final String URL = "https://inloop-contacts.appspot.com/_ah/api";
    static final String CONTACT_ENDPOINT = "/contactendpoint/v1/";
    static final String ORDER_ENDPOINT = "/orderendpoint/v1/";

    static class ContactWrapper {
        final List<Contact> items;

        public ContactWrapper(List<Contact> items) {
            this.items = items;
        }
    }

    static class Contact {
        final Long id;
        final String name;
        final String phone;
        final String pictureUrl;
        final String kind;

        public Contact(Long id, String name, String phone, String pictureUrl, String kind) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.pictureUrl = pictureUrl;
            this.kind = kind;
        }
    }

    static class OrderWrapper {
        final List<Order> items;

        public OrderWrapper(List<Order> items) {
            this.items = items;
        }
    }

    static class Order {
        final String name;
        final int count;
        final String kind;

        public Order(String name, int count, String kind) {
            this.name = name;
            this.count = count;
            this.kind = kind;
        }
    }

    interface ContactService {
        @GET("contact")
        Call<ContactWrapper> listContacts();

        @GET("order/{id}")
        Call<OrderWrapper> listOrders(@Path("id") long contactId);
    }
}
