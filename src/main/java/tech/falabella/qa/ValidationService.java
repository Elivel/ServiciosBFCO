package tech.falabella.qa;

import tech.falabella.qa.tuple.Tuple;

import java.util.Collection;

public interface ValidationService {

    /**
     * Recupera los datos de entrada (por un CVS o exportando desde SSRS)
     *
     * @return Datos tratados y sanitizados
     */
    <T extends Tuple> Collection<T> getInputData();

    /**
     * Recupera los datos almacenados en la fuente de confianza (por un CSV o consulta a BBDD)
     *
     * @return Datos tratados y sanitizados
     */
    <T extends Tuple> Collection<T> getPersistenceData();

    /**
     * Compara los datos suministrados
     *
     * @param inputData
     * @param persistenceData
     * @param <T>
     * @return lista de registros inconsistentes
     */
    default <T extends Tuple> Collection<T> processElements(Collection<T> inputData,
                                                            Collection<T> persistenceData) {
        return persistenceData.parallelStream()
                .filter(it -> !inputData.contains(it))
                .toList();
    }
}
