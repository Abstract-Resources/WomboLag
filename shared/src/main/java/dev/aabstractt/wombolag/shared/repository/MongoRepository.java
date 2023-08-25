package dev.aabstractt.wombolag.shared.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import dev.aabstractt.wombolag.shared.AbstractLoader;
import dev.aabstractt.wombolag.shared.repository.codec.Storable;
import lombok.NonNull;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class MongoRepository<O extends Storable> {

    private @Nullable MongoCollection<Document> mongoCollection = null;

    public void init(@NonNull String uri) {
        System.out.println("uri: " + uri);

        ConnectionString connectionString = new ConnectionString(uri);
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            System.out.println("Mongodb > " + connectionString.getDatabase());

            if (connectionString.getDatabase() == null || connectionString.getCollection() == null) {
                return;
            }

            MongoDatabase mongoDatabase = mongoClient.getDatabase(connectionString.getDatabase());
            this.mongoCollection = mongoDatabase.getCollection(connectionString.getCollection());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void deleteOne(@NonNull String id) {
        if (this.mongoCollection == null) {
            throw new IllegalStateException("MongoCollection cannot be null!");
        }

        try {
            this.mongoCollection.deleteOne(new Document("_id", id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertOne(@NonNull O storable) {
        if (this.mongoCollection == null) {
            throw new IllegalStateException("MongoCollection cannot be null!");
        }

        String json = AbstractLoader.GSON.toJson(storable);
        if (json.isBlank() || json.isEmpty()) {
            throw new IllegalArgumentException("JSON cannot be null or empty!");
        }

        this.mongoCollection.replaceOne(
                Filters.eq("_id", storable.getId()),
                Document.parse(json),
                new ReplaceOptions().upsert(true)
        );
    }

    public @Nullable O findOne(@NonNull String key, @NonNull String value) {
        if (this.mongoCollection == null) {
            throw new IllegalStateException("MongoCollection cannot be null!");
        }

        Document document = this.mongoCollection.find(Filters.eq(key, value)).first();
        if (document == null) {
            return null;
        }

        return AbstractLoader.GSON.fromJson(document.toJson(), (Class<O>) Storable.class);
    }

    public @Nullable O findOne(@NonNull String id) {
        if (this.mongoCollection == null) {
            throw new IllegalStateException("MongoCollection cannot be null!");
        }

        Document document = this.mongoCollection.find(Filters.eq("_id", id)).first();
        if (document == null) {
            return null;
        }

        return AbstractLoader.GSON.fromJson(document.toJson(), (Class<O>) Storable.class);
    }

    public @NonNull List<O> findAll() {
        if (this.mongoCollection == null) {
            throw new IllegalStateException("MongoCollection cannot be null!");
        }

        return this.mongoCollection.find()
                .map(document -> AbstractLoader.GSON.fromJson(document.toJson(), (Class<O>) Storable.class))
                .into(new ArrayList<>());
    }
}