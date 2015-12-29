package org.rs.app.meam.config;

import java.sql.Types;
import org.hibernate.dialect.DerbyTenSevenDialect;

/**
 *
 * @author srini
 */
public class DerbyDialect extends DerbyTenSevenDialect {

    public DerbyDialect() {
        System.out.println("called DerbyDialect.....");
        registerColumnType(Types.BOOLEAN, "SHORT");
    }
}
