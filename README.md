# Приложение для прослушивания музыки VKMusic
Данное приложение разработано для прослушивания музыки с сервиса Vk.com

## Содержание
- [Технологии](#технологии)
- [Использование](#использование)
- [Тестирование](#тестирование)
- [Contributing](#contributing)
- [FAQ](#faq)
- [To do](#to-do)
- [Команда проекта](#команда-проекта)
- [Источники](#источники)

## Технологии
- [Android Studio 2022](https://developer.android.com/studio)
- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://www.jetbrains.com/ru-ru/lp/compose-multiplatform/)

## Использование
Для работы с приложением необходимо скачать apk файл и войти в свой аккаунт Vk.com.

После входа будет отображён главный экран:

![Screenshot_20240209_111339](https://github.com/orderyoo/VKM/blob/orderyoo-attachments/Screenshot_20240209_111339_com.example.vkm.jpg)

Отсюда можно перейти в любой из сохранённых плейлистов, к автору аудио, в строку поиска и открыть окно управления плеером (оно доступно по умолчанию на всех экранах в виде нижнего скорлл списка, в его шапке указан играющее сейчас аудио)

Плейлист:
![Screenshot_20240209_111345](https://github.com/orderyoo/VKM/blob/orderyoo-attachments/Screenshot_20240209_111345_com.example.vkm.jpg)

Автор:
![Screenshot_20240209_114358](https://github.com/orderyoo/VKM/blob/orderyoo-attachments/Screenshot_20240209_114358_com.example.vkm.jpg)

Поиск:
![Screenshot_20240209_114342](https://github.com/orderyoo/VKM/blob/orderyoo-attachments/Screenshot_20240209_114342_com.example.vkm.jpg)


## Разработка
### Тестирование
В проекте использовались следующие виды тестирования: Интеграционное тестирование 
Итоги тестирования:
### Итеграционное тестирование 

Граф программы:

![graph](https://github.com/orderyoo/VKM/blob/orderyoo-attachments/graph.png)

T1: 1-2-3-5-6;
T2: 1-2-4-3-5-6;

Путь 1: создание плера-активити выбора-активити основная-действия-выход
Путь 2: создание плеера-активти выбора-активити регистрации-активити основная-дейстивия-выход

### Тест-кейсы
 
![image](https://github.com/orderyoo/VKM/blob/orderyoo-attachments/image.png)

## Contributing
Сообщения о багах и ошибках присылать на почту: markovmaks895@gmail.com

## FAQ 
### Как открыть экран плеера?
- Снизу экрана есть шапка плеера (сплошная полоса), потянув за неё или нажав, откроет экран управления воспроизведением аудио
### Зачем вы разработали этот проект?
- я ПрАгРаМмИсТ и люблю комфорт, решил добавить его и в свой телефон

  ## Команда проекта
- [Марков Максим](https://t.me/order_yo) — Главный разработчик, программист, тестировщик, дизайнер, страдалец и мученник

## Источники 

https://developer.android.com/
https://open.ai/chat.gpt










   
