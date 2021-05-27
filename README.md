# バックエンド サンプルアプリ

## 動作環境

実行環境に以下のソフトウェアがインストールされている事を前提とします。

* Java Version：11

以下は、本手順では事前準備不要です。

|ソフトウェア|説明|
|:---|:---|
|APサーバ|このアプリケーションはJetty9(Apache Mavenで実行した場合)、Tomcat9(Dockerコンテナを実行した場合)を使用しています。|
|DBサーバ|このアプリケーションはH2 Database Engine(以下H2)を組み込んであるため、別途インストールの必要はありません。|

## テスト実行

以下コマンドでテスト実行してください。

```bash
./mvnw test
```

Windowsの場合は、以下コマンドでテスト実行してください。

```bash
mvnw.cmd test
```

コンソールに`BUILD SUCCESS`が出力されていれば、テスト結果は無事成功です。

## サンプルアプリの起動

サンプルアプリの起動はApache Mavenを利用する方法と、Dockerを利用する方法があります。

### Apache Mavenを利用する方法

以下のコマンドを実行してください。

```bash
./mvnw jetty:run
```

Windowsの場合は、以下コマンドでテスト実行してください。

```bash
mvnw.cmd jetty:run
```

コンソールに`Started Jetty Server`が出力されていれば、無事に起動成功です。

### Dockerを利用する方法

以下のコマンドを実行してください。

```bash
docker run --rm -d -p 9080:8080 --name todo-app-backend todo-app-backend:latest
```

H2に格納されているデータを永続化したい場合は、[Volume](https://docs.docker.com/storage/volumes/) を作成します。

```bash
docker run --rm -d -p 9080:8080 --name todo-app-backend -v todo-app-backend-volume:/usr/local/tomcat/h2 todo-app-backend:latest
```

## Proxy環境下でサンプルアプリを動かす場合

### Apache Mavenを利用してサンプルアプリを動かす場合

`settings.xml`にProxyの情報を設定する必要があります。  
設定内容の詳細や、`settings.xml`の配置場所については、 [Apache Maven Project - Settings Reference - Introduction](https://maven.apache.org/settings.html#settings-reference) を参照してください。

以下は主なProxy設定情報です。
```xml
...
  <proxies>
    <proxy>
      <id>proxy-http</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>[プロキシサーバのホスト]</host>
      <port>[プロキシ用ポート]</port>
      <username>[プロキシユーザ]</username>
      <password>[プロキシパスワード]</password>
      <nonProxyHosts>[除外したいプロキシホスト]</nonProxyHosts>
    </proxy>
    <proxy>
      <id>proxy-https</id>
      <active>true</active>
      <protocol>https</protocol>
      <host>[プロキシサーバのホスト]</host>
      <port>[プロキシ用ポート]</port>
      <username>[プロキシユーザ]</username>
      <password>[プロキシパスワード]</password>
      <nonProxyHosts>[除外したいプロキシホスト]</nonProxyHosts>
    </proxy>
  </proxies>
...
```

### Dockerを利用してサンプルアプリを動かす場合

DockerをProxy環境で動かす方法の一つとして、環境変数にプロキシ情報を設定します。
* HTTP_PROXY
* HTTPS_PROXY
* NO_PROXY

その他の方法や詳細については、[Configure Docker to use a proxy server](https://docs.docker.com/network/proxy/) を参照してください。

