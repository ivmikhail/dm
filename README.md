# Download Manager

Pet project

## Coding problem

Write a program that can be used to download data from multiple sources and protocols to local disk. The list of sources will be given as input in the form of urls (e.g. http://my.file.com/file, ftp://other.file.com/other, sftp://and.also.this/ending etc).

The program should download all the sources, to a configurable location (file name should be uniquely determined from the url) and then exit.

In your code, please consider:

1. The program should extensible to support different protocols
2. Some sources might very big (more than memory)
3. Some sources might be very slow, while others might be fast
4. Some sources might fail in the middle of download
5. We don't want to have partial data in the final location in any case

You can implement the code Scala or Java and also include tests.

## Usage

```
java [-Dapp.properties=/path/to/app.properties] -jar dm.jar /path/to/urls.txt
```
* urls.txt - The text file must have one URL per line 
* properties will be loaded from classpath if not specified, default:
````
userAgent="DmBot/1.0 (+https://github.com/ivmikhail/dm)"
downloads.directory=downloads
#maximum number of simultaneous downloads
downloads.maxRunningCount=10
url.connection.timeoutMillis=20000
````

![Usage screencast](screencast.gif)

## Points of improvement

* Parallel file downloading
* Many error handling and special cases omitted
* Progress bar for URL
* ...
