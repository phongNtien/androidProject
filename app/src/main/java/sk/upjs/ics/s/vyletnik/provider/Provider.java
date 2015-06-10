package sk.upjs.ics.s.vyletnik.provider;


import android.provider.BaseColumns;

public interface Provider {

    public interface Record extends BaseColumns {
        public static final String TABLE_NAME = "vyletnik";

        public static final String NAME = "name";

        public static final String CONTENT = "content";

        public static final String TIMESTAMP = "timestamp";

        public static final String PHOTO = "photo";
    }
}
