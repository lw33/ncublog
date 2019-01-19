package cn.edu.ncu.ncublog.config;

/**
 * @Author lw
 * @Date 2018/9/2
 **/

//@Configuration
//@ConfigurationProperties("classpath:redis.properties")
public class RedisConfig {
    /*private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }


    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());
        return template;
    }*/

}
