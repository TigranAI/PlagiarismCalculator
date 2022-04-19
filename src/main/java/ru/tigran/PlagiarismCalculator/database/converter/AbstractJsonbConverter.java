package ru.tigran.PlagiarismCalculator.database.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.sql.SQLException;

public abstract class AbstractJsonbConverter<T> implements AttributeConverter<T, PGobject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonbConverter.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(T ts) throws IllegalStateException {
        PGobject result = new PGobject();
        result.setType("jsonb");

        try {
            result.setValue(MAPPER.writeValueAsString(ts));
        } catch (SQLException | JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException(e);
        }

        return result;
    }

    @Override
    public T convertToEntityAttribute(PGobject pGobject) {
        if (pGobject == null || pGobject.isNull()) return null;

        try {
            return MAPPER.readValue(pGobject.getValue(), getType());
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    protected abstract TypeReference<T> getType();
}
