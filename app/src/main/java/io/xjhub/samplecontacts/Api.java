package io.xjhub.samplecontacts;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

final class Api {
    // Prevents accidentally instantiating this class
    private Api() {}

    static final String API_URL = "https://inloop-contacts.appspot.com/_ah/api/contactendpoint/v1/";

    static class ContactWrapper {
        final List<Contact> items;

        public ContactWrapper(List<Contact> items) {
            this.items = items;
        }
    }

    static class Contact {
        final BigInteger id;
        final String name;
        final String phone;
        final String pictureUrl;
        final String kind;

        public Contact(BigInteger id, String name, String phone, String pictureUrl, String kind) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.pictureUrl = pictureUrl;
            this.kind = kind;
        }
    }

    interface ContactService {
        @GET("contact")
        Call<ContactWrapper> listContacts();
    }
}
