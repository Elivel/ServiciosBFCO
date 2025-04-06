package tech.falabella.qa.port;

import tech.falabella.qa.CommandArgs;
import tech.falabella.qa.report.Tuple;

import java.util.Collection;

public interface StoragePort {

    <T extends Tuple> void generate(Collection<T> result, CommandArgs args);

}
