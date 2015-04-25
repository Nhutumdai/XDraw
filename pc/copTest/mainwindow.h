#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QtGui>
#include <QtCore>
#include <QGraphicsScene>
#include <QGraphicsView>
#include <QGraphicsRectItem>
#include <QColorDialog>
#include <QMouseEvent>
#include <QFile>
#include <QXmlStreamReader>
#include <QFileDialog>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_pushButton_clicked();
    void on_pushButton_2_clicked();
    void mousePressEvent(QMouseEvent *e);
    void on_xml_reader_clicked();
    void processProject();
    void processRectangles();
    void processRectangle();
    void drawRectangle(qreal x, qreal y);

    void on_writeButton_clicked();

private:
    Ui::MainWindow *ui;
    QGraphicsScene *scene;
    QGraphicsRectItem *rectangle;
    QPoint startPos;
    QPoint endPos;
    bool inDrawing;
    QVector<QLine> lines;
    qreal posX;
    qreal posY;
    qreal myHeight=50;
    qreal myWidth=80;
    QXmlStreamReader xml;
};

#endif // MAINWINDOW_H
