#-------------------------------------------------
#
# Project created by QtCreator 2015-03-20T16:33:15
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = copTest
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp

HEADERS  += mainwindow.h \
    linklist.h \
    ../ssh/include/libssh/libssh.h \
    ../ssh/include/libssh/legacy.h

FORMS    += mainwindow.ui
