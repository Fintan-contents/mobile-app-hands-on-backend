# バックエンド サンプルアプリ

## DBコンテナの起動

テスト実行前にDBコンテナを起動します。コマンドプロンプトまたはターミナルを使用してルートディレクトリへ移動して、以下を実行してください。

```bash
docker-compose -f docker/docker-compose.dev.yml up -d
```

## テスト実行

DBコンテナ起動後、以下でテスト実行してください。

```bash
mvn test
```

実行結果、コンソールに`BUILD SUCCESS`が出力されていれば、テスト結果は無事成功です。

## バックエンドアプリの起動

バックエンドアプリの起動は以下のコマンドを実行してください。
※起動時にデータベースに接続するため、DBコンテナを起動していない場合は[DBコンテナの起動](#DBコンテナの起動)を参照して起動してください。

```bash
mvn jetty:run
```

実行結果、コンソールに`Started Jetty Server`が出力されていれば、無事に起動成功です。

## Container Imageをローカルに作成

Container Imagesをビルドしてローカルにインストールする場合は、mvnでjibをつかってコンテナを作成する。

```
mvn package jib:dockerBuild -DskipTests
```

コンテナを利用して起動する場合は `docker/if-app-container-image-published.yml` を利用する。

```
docker-compose -f docker/if-app-container-image-published.yml
```
