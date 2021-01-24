# GeppoExtractor : Automatic frame data extractor

GeppoExtractor is a Java application that automatically downloads frame data of all the Tekken characters from the website [Geppopotamus](http://geppopotamus.info/game/tekken7fr/index_en.htm) and transforms it into a JSON format.

These JSON files are used by the [Mokujin](https://www.github.com/abosma/mokujin) Discord bot to quickly give frame data to whichever person asks for it.

## Dependencies

The application uses Spring in combination with JSoup to crawl data from the website.