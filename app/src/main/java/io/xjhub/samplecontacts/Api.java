package io.xjhub.samplecontacts;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class Api {

    public static final String API_URL = "https://inloop-contacts.appspot.com/_ah/api/contactendpoint/v1/";

    public static class ContactWrapper {
        public final List<Contact> items;

        public ContactWrapper(List<Contact> items) {
            this.items = items;
        }
    }

    public static class Contact {
        public final BigInteger id;
        public final String name;
        public final String phone;
        public final String kind;

        public Contact(BigInteger id, String name, String phone, String kind) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.kind = kind;
        }
    }

    public interface ContactService {
        @GET("contact")
        Call<ContactWrapper> listContacts();
    }
}
