# JDBI + Spring @Transactional

```java
@Configuration
public class Config {
    @Bean
    public Jdbi createJdbi(DataSource dataSource) {
        return Jdbi.create(new TransactionAwareDataSourceProxy(dataSource));
    }
}
```

and

```java
@Repository
class MyRepository {
    private final Jdbi jdbi;

    @Autowired
    public MyRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Transactional
    public void insert() {
        jdbi.useHandle(h -> {
            h.createUpdate("insert into hello (msg) values ('hello')");
            h.createUpdate("insert into hello (msg) values ('there')");
        });
    }
}
```
