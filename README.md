# AzReco Speech Recognition API Java example
Example project in Java to help you integrate with our speech recognition API.

This is an example Java project for uploading audio(.wav , .mp3 , .opus, .m4a) or video(.mp4 , .mkv) file and saving the transcript into a .json file.

# Supporting languages
AZERBAIJANI (az-AZ)

TURKISH  (tr-TR)

RUSSIAN  (ru-RU)

# Requirements

You will need to have the Java compiler and Maven tool installed in your operation system.
Go into the project folder, execute "mvn compile" and "mvn install" commands in terminal. The last command creates executable jar in the target folder.

# Usage example:

java -jar azreco-asr-java-1.0.jar -a audio/example-ru.wav -l ru-RU -i api_user_id -k api_token -o example-ru.json 

In this example the application uploads 'example.wav', transcribes it using our ru-RU speech to text and saves the resulting transcription as 'example.json' when the transcribing process finished.


# How to get user id and token?

To get user id and API token, send a request to info@azreco.az.

To confirm your request, we will ask you some details about your purpose in using API.
