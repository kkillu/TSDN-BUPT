package com.huawei.tsdn.sampleapp.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class JsonCodec<T> {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Encodes the specified entity into JSON.
     *
     * @param entity
     *            entity to encode
     * @return JSON node
     * @throws java.lang.UnsupportedOperationException
     *             if the codec does not support encode operations
     */
    public ObjectNode encode(T entity) {
        throw new UnsupportedOperationException("encode() not supported");
    }

    /**
     * Decodes the specified entity from JSON.
     *
     * @param json
     *            JSON to decode
     * @return decoded entity
     * @throws java.lang.UnsupportedOperationException
     *             if the codec does not support decode operations
     */
    public T decode(ObjectNode json) {
        throw new UnsupportedOperationException("decode() not supported");
    }

    /**
     * Encodes the collection of the specified entities.
     *
     * @param entities
     *            collection of entities to encode
     * @return JSON array
     * @throws java.lang.UnsupportedOperationException
     *             if the codec does not support encode operations
     */
    public ArrayNode encode(Iterable<T> entities) {
        ArrayNode result = mapper.createArrayNode();
        for (T entity : entities) {
            ObjectNode node = encode(entity);
            if (node != null) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * Decodes the specified JSON array into a collection of entities.
     *
     * @param json
     *            JSON array to decode
     * @return collection of decoded entities
     * @throws java.lang.UnsupportedOperationException
     *             if the codec does not support decode operations
     */
    public List<T> decode(ArrayNode json) {
        List<T> result = new ArrayList<>();
        for (JsonNode node : json) {
            T entity = decode((ObjectNode) node);
            if (entity != null) {
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * Gets a child Object Node from a parent by name. If the child is not found
     * or does nor represent an object, null is returned.
     *
     * @param parent
     *            parent object
     * @param childName
     *            name of child to query
     * @return child object if found, null if not found or if not an object
     */
    protected static ObjectNode get(ObjectNode parent, String childName) {
        JsonNode node = parent.path(childName);
        return node.isObject() && !node.isNull() ? (ObjectNode) node : null;
    }

    /**
     * Gets a child Object Node from a parent by index. If the child is not
     * found or does nor represent an object, null is returned.
     *
     * @param parent
     *            parent object
     * @param childIndex
     *            index of child to query
     * @return child object if found, null if not found or if not an object
     */
    protected static ObjectNode get(JsonNode parent, int childIndex) {
        JsonNode node = parent.path(childIndex);
        return node.isObject() && !node.isNull() ? (ObjectNode) node : null;
    }

    /**
     * Encodes the specified entity into create JSON.
     *
     * @param entity
     *            entity to encode
     * @return JSON node
     * @throws java.lang.UnsupportedOperationException
     *             if the codec does not support encode operations
     */
    public ObjectNode encodeCreateTunnelJson(T entity) {
        throw new UnsupportedOperationException("encodeCreateTunnelJson() not supported");
    }

    /**
     * Encodes the specified entity into create JSON.
     *
     * @param entity
     *            entity to encode
     * @return JSON node
     * @throws java.lang.UnsupportedOperationException
     *             if the codec does not support encode operations
     */
    public ObjectNode encodeModifyTunnelJson(T entity) {
        throw new UnsupportedOperationException("encodeCreateTunnelJson() not supported");
    }
    
    /**
     * Encodes the specified entity into update JSON.
     *
     * @param entity
     *            entity to encode
     * @return JSON node
     * @throws java.lang.UnsupportedOperationException
     *             if the codec does not support encode operations
     */
    public String encodeUpdateTunnelJson(T entity) {
        throw new UnsupportedOperationException("encodeUpdateTunnelJson() not supported");
    }

    public ObjectMapper mapper() {
        return mapper;
    }
}
