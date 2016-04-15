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
 * 连接shopping数据库
 * Created by Sunny Wu.
 * 15/12/7
 */
public class ShoppingDBModule extends PrivateModule {

    @Override
    protected void configure() {
        install(new org.mybatis.guice.MyBatisModule(){
            @Override
            protected void initialize() {
                environmentId("shopping");
                //开启驼峰式自动映射
                mapUnderscoreToCamelCase(true);
                bindDataSourceProviderType(ShoppingDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);

                //只针对shopping数据库的Mapper,不可以将一个Mapper多Module进行Add
                addMapperClass(OrderMapper.class);
                addMapperClass(OrderShipMapper.class);
                addMapperClass(OrderLineMapper.class);
                addMapperClass(OrderSplitMapper.class);

            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.无须更改
         * 如果需要取SqlSession:
         * @Named("shopping")
         * @Inject
         * private SqlSession sqlsession;
         */
        bind(SqlSession.class).annotatedWith(Names.named("shopping")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("shopping"));

        /**
         * bind service for controller or other service inject. 绑定shopping数据库所对应的Service
         */
        bind(OrderService.class).to(OrderServiceImpl.class);
        bind(OrderShipService.class).to(OrderShipServiceImpl.class);
        bind(OrderLineService.class).to(OrderLineServiceImpl.class);
        bind(OrderSplitService.class).to(OrderSplitServiceImpl.class);

        //必须expose
        expose(OrderService.class);
        expose(OrderShipService.class);
        expose(OrderLineService.class);
        expose(OrderSplitService.class);
    }

    @Singleton
    public static class ShoppingDataSourceProvider implements Provider<DataSource> {
        final DBApi db;

        @Inject
        public ShoppingDataSourceProvider(final DBApi db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            return db.getDatabase("shopping").getDataSource();
        }
    }
}
