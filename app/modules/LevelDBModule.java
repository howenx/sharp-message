package modules;

import com.google.inject.AbstractModule;

/**
 * 启动leveldb
 * Created by howen on 16/2/19.
 */
public class LevelDBModule extends AbstractModule {

    protected void configure() {
        bind(LevelFactory.class).asEagerSingleton();
        bind(NewScheduler.class);
    }
}
