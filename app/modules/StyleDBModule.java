package modules;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import mapper.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.session.SqlSessionManagerProvider;
import play.db.DBApi;
import service.*;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * Style database sqlsession inject.注意,每个库都需要一个PrivateModule,而且有自己独立的Service注入和不同的SQLsession命名绑定
 * Created by howen on 15/11/11.
 */
public class StyleDBModule extends PrivateModule{
    @Override
    protected void configure() {
        install(new org.mybatis.guice.MyBatisModule() {
            @Override
            protected void initialize() {
                environmentId("style");
                //开启驼峰自动映射
                mapUnderscoreToCamelCase(true);

                bindDataSourceProviderType(StyleDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);

                //只针对style数据库的Mapper,不可以将一个Mapper多Module进行Add
                addMapperClass(ItemMapper.class);
                addMapperClass(InventoryMapper.class);
            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.无须更改
         * 如果需要取SqlSession:
         * @Named("style")
         * @Inject
         * private SqlSession sqlsession;
         */
        bind(SqlSession.class).annotatedWith(Names.named("style")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("style"));

        /**
         * bind service for controller or other service inject. 绑定style数据库所对应的Service
         */
        bind(ItemService.class).to(ItemServiceImpl.class);
        bind(InventoryService.class).to(InventoryServiceImpl.class);

        //必须expose
        expose(ItemService.class);
        expose(InventoryService.class);
    }

    @Singleton
    public static class StyleDataSourceProvider implements Provider<DataSource> {
        final DBApi db;

        @Inject
        public StyleDataSourceProvider(final DBApi db) {
            this.db = db;
        }


        @Override
        public DataSource get() {
            return db.getDatabase("default").getDataSource();
        }
    }
}
