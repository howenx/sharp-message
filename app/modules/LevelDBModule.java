package modules;

import com.google.inject.AbstractModule;
import redis.clients.jedis.Jedis;
import util.LeveldbLoad;
import util.LogUtil;
import util.RedisPool;
import util.SysParCom;

/**
 * 启动leveldb
 * Created by howen on 16/2/19.
 */
public class LevelDBModule extends AbstractModule {

    protected void configure() {
        bind(LevelFactory.class).asEagerSingleton();
        bind(NewScheduler.class);
        bind(RemoteActorModule.class).asEagerSingleton();
        bind(LevelDBPersist.class).asEagerSingleton();
        bind(SysParCom.class).asEagerSingleton();
        bind(LogUtil.class).asEagerSingleton();
        bind(RedisPool.class).asEagerSingleton();
        bind(LeveldbLoad.class).asEagerSingleton();
    }
}
