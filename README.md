# バックエンド サンプルアプリ

## 動作環境

実行環境に以下のソフトウェアがインストールされている事を前提とします。

* Java Version：8以降

以下は、本手順では事前準備不要です。

|ソフトウェア|説明|
|:---|:---|
|APサーバ|このアプリケーションはJetty9(Apache Mavenで実行した場合)、Tomcat9(Dockerコンテナを実行した場合)を組み込んであるため、別途インストールの必要はありません。|
|DBサーバ|このアプリケーションはH2 Database Engine(以下H2)を組み込んであるため、別途インストールの必要はありません。|

## テスト実行

以下コマンドでテストを実行してください。

```bash
./mvnw test
```

Windowsの場合は、以下コマンドでテストを実行してください。

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

停止する場合は、`Ctrl + C` を押してください。


### Dockerを利用する方法

以下のコマンドを実行して起動ください。

```bash
docker run --rm -p 9080:8080 ghcr.io/ws-4020/mobile-app-hands-on-backend:latest
```

H2に格納されているデータを永続化したい場合は、[Volume](https://docs.docker.com/storage/volumes/)を作成します。

```bash
docker run --rm -p 9080:8080 -v mobile-app-hands-on-backend-volume:/usr/local/tomcat/h2 ghcr.io/ws-4020/mobile-app-hands-on-backend:latest
```

## Proxy環境下でサンプルアプリを動かす場合

### Apache Maven Wrapperを利用してサンプルアプリを動かす場合

以下の設定ファイルにプロキシ情報を設定する必要があります。設定ファイルが存在しない場合は、新規作成してください。
* `[プロジェクトルート]/.mvn/jvm.config`
* `[OSのユーザホームディレクトリ]/.m2/settings.xml`

`jvm.config`の設定例
```properties
-Dhttp.proxyHost=[プロキシサーバのホスト]
-Dhttp.proxyPort=[プロキシサーバのポート]
-Dhttp.proxyUser=[プロキシサーバの認証ユーザ]
-Dhttp.proxyPassword=[プロキシサーバの認証パスワード]
-Dhttps.proxyHost=[プロキシサーバのホスト]
-Dhttps.proxyPort=[プロキシサーバのポート]
-Dhttps.proxyUser=[プロキシサーバの認証ユーザ]
-Dhttps.proxyPassword=[プロキシサーバの認証パスワード]
-Djdk.http.auth.tunneling.disabledSchemes=
```
`settings.xml`の設定内容の詳細については、 [Apache Maven Project - Settings Reference - Introduction](https://maven.apache.org/settings.html#settings-reference) を参照してください。

`settings.xml`の設定例
```xml
...
  <proxies>
    <proxy>
      <id>proxy-http</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>[プロキシサーバのホスト]</host>
      <port>[プロキシサーバのポート]</port>
      <username>[プロキシサーバの認証ユーザ]</username>
      <password>[プロキシサーバの認証パスワード]</password>
    </proxy>
    <proxy>
      <id>proxy-https</id>
      <active>true</active>
      <protocol>https</protocol>
      <host>[プロキシサーバのホスト]</host>
      <port>[プロキシサーバのポート]</port>
      <username>[プロキシサーバの認証ユーザ]</username>
      <password>[プロキシサーバの認証パスワード]</password>
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
