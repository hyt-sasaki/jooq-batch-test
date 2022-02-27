# Jooq batch test
## 開始方法

```bash
# mysqlのコンテナを起動 (テストデータとしてsakilaを投入)
$ docker compose -f docker/docker-compose.yml up -d
# sakilaのデータをもとにjooqのコードを生成
$ ./gradlew generateJooq
# batchの起動テストを実行
$ ./gradlew test
```