package modules;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import mapper.IDMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.session.SqlSessionManagerProvider;
import play.db.DBApi;
import service.IDService;
import service.IDServiceImpl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * Account数据库连接Module
 * Created by howen on 15/11/11.
 */
public class AccountDBModule extends PrivateModule {
    @Override
    protected void configure() {
        install(new org.mybatis.guice.MyBatisModule() {
            @Override
            protected void initialize() {
                environmentId("account");
                //开启驼峰自动映射
                mapUnderscoreToCamelCase(true);

                bindDataSourceProviderType(AccountDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);

                //只针对account数据库的Mapper,不可以将一个Mapper多Module进行Add
                addMapperClass(IDMapper.class);
            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.无须更改
         */
        bind(SqlSession.class).annotatedWith(Names.named("account")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("account"));

        /**
         * bind service for controller or other service inject. 绑定account数据库所对应的Service
         */
        bind(IDService.class).to(IDServiceImpl.class);

        //必须expose
        expose(IDService.class);
    }

    @Singleton
    public static class AccountDataSourceProvider implements Provider<DataSource> {
        final DBApi account_db;

        @Inject
        public AccountDataSourceProvider(final DBApi db) {

            this.account_db = db;
        }

        @Override
        public DataSource get() {
            return account_db.getDatabase("account").getDataSource();
        }
    }
}
