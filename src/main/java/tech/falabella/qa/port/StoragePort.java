package tech.falabella.qa.port;

import tech.falabella.qa.CommandArgs;
import tech.falabella.qa.dto.Result;

import java.util.Collection;

public interface StoragePort {

    void generate(Result result, CommandArgs args);

}
