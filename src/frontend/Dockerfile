# Etapa 1: Build do projeto
FROM gradle:8.8-jdk17 as build-stage

# Permite instalar pacotes
USER root
RUN apt-get update && apt-get install -y unzip wget

# Variáveis do Android SDK
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV ANDROID_HOME=$ANDROID_SDK_ROOT
ENV PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools

# Instala o Android Command Line Tools
RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    cd $ANDROID_SDK_ROOT/cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O cmdline-tools.zip && \
    unzip cmdline-tools.zip -d tmp && \
    mv tmp/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest && \
    rm -rf cmdline-tools.zip tmp

# Aceita licenças e instala componentes Android
RUN yes | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --licenses && \
    $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Cria diretório do projeto
WORKDIR /app
COPY . .

# Cria local.properties apontando para o SDK
RUN echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties

# Faz build do projeto
RUN ./gradlew build --no-daemon

# Etapa 2: Runtime (opcional)
FROM gradle:8.8-jdk17 as runtime
COPY --from=build-stage /app /app
WORKDIR /app
CMD ["./gradlew", "build"]
