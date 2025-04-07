package tech.falabella.qa.port;

import tech.falabella.qa.CommandArgs;

import java.util.Collection;

public interface StoragePort {

    void generate(Collection<String> result, CommandArgs args);

}
