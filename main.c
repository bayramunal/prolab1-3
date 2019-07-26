#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

struct kapi
{

    ///kapi tipini saklayacak pointer char dizisi ve bu dizinin uzunlugu karakter bazinda sayilip hesaplanip belirlenir
    char *kapiTipi;
    int kapiTipiUzunlugu;

    int girisSayisi;

    /// cikis ve cikis degeri
    char cikis;
    int cikisDegeri;

    ///kapi girisleri belirlendikten sonra deger.txt icerisinden giris degerleri initial edilir
    char * girisler;
    int * girisDegerleri;

    ///devrenin kapi gecikmesi
    int kapiGecikmesi;
};

struct tempKapi
{
    char cikis;
    int cikisDegeri;
};

int andKapisi(int p, int q);
int orKapisi(int p, int q);
int xorKapisi(int p, int q);
int notKapisi(int p);
int nandKapisi(int p, int q);
int norKapisi(int p, int q);
char devreninAnaCikisi(char dosyaAdi[]);
void ilkDegerleriBelirle(char degerDosyasi[], struct kapi kapilar[], int boyut, char anaGiris, int * anaGirisDegeri);
void degerDuzenle(struct kapi kapilar[], int boyut);
void baskaDosya();
int devredekiKapiSayisi(char dosyaAdi[]);
void devreninAnaGirisleri(char dosyaAdi[], char devreGirisleri[], int boyut);
int devredekiKapiSayisi(char dosyaAdi[]);
int devreninAnaGirisSayisi(char dosyaAdi[]);
void hKomutunuCalistir(struct kapi kapilar[], int kapiSayisi, char degisecekler[], int degiseceklerinSayisi, char anaCikis, FILE *dosya2);
void butunDegerleriGoster(struct kapi kapilar[], int devreKapiSayisi, struct kapi baskaDosyaKapilar[], int baskaDosyaKapiSayisi, char devreAnaCikisi, int anaCikisDegeri, FILE *dosya);
int KKomutu(FILE *dosya2);


void degerGuncelle(struct kapi *kapilar, int boyut, char guncellenenGiris, int guncellenenDeger)
{
    bool guncellendiMi = false;

    for ( int i = 0; i < boyut; i++)
    {
        for ( int j = 0; j < kapilar[i].girisSayisi; j++)
        {
            if ( kapilar[i].girisler[j] == guncellenenGiris)
            {
                guncellendiMi = true;
                kapilar[i].girisDegerleri[j] = guncellenenDeger;
            }
        }
    }

    if ( guncellendiMi == false)
    {
        for ( int i = 0; i < boyut; i++)
        {
            if ( kapilar[i].cikis == guncellenenGiris)
            {
                kapilar[i].cikisDegeri = guncellenenDeger;
                break;
            }
        }
    }
}

int kapiHesapla(char kapi[], int degerler[], int boyut)
{
    int sonuc = -1;
    if(strstr(kapi,"nand") != NULL ||strstr(kapi,"NAND") != NULL)
    {
        sonuc = nandKapisi(degerler[0], degerler[1]);

        for ( int i = 2; i < boyut; i++)
        {
            sonuc = nandKapisi(sonuc, degerler[i]);
        }
    }

    else if(strstr(kapi,"xor") != NULL || strstr(kapi,"XOR") != NULL)
    {
        sonuc = xorKapisi(degerler[0], degerler[1]);

        for ( int i = 2; i < boyut; i++)
        {
            sonuc = xorKapisi(sonuc, degerler[i]);
        }
    }

    else if(strstr(kapi,"nor") != NULL || strstr(kapi,"NOR") != NULL)
    {
        sonuc = norKapisi(degerler[0], degerler[1]);
        for ( int i = 2; i < boyut; i++)
        {
            sonuc = norKapisi(sonuc, degerler[i]);
        }

    }

    else if(strstr(kapi,"not") != NULL || strstr(kapi,"NOT") != NULL) /// DUZENLENECEK
    {
        sonuc = notKapisi(degerler[0]);

        for ( int i = 1; i < boyut; i++)
        {
            sonuc = notKapisi(degerler[i]);
        }
    }

    else if(strstr(kapi,"and") != NULL || strstr(kapi,"AND") != NULL)
    {
        sonuc = andKapisi(degerler[0], degerler[1]);

        for ( int i = 2; i < boyut; i++)
        {
            sonuc = andKapisi(sonuc, degerler[i]);
        }
    }

    else if(strstr(kapi,"or") != NULL || strstr(kapi,"OR") != NULL)
    {
        sonuc = orKapisi(degerler[0], degerler[1]);

        for ( int i = 2; i < boyut; i++)
        {
            sonuc = orKapisi(sonuc, degerler[i]);
        }
    }

    return sonuc;
}

void hesapla(struct kapi devreKapilar[], int devreKapiSayisi, struct kapi baskaDosyaKapilar[], int baskaDosyaKapiSayisi, char anaCikis, FILE *dosya)
{

    printf("\n");
    fprintf(dosya, "\n");
    int toplamBoyut = devreKapiSayisi+baskaDosyaKapiSayisi;
    struct kapi kapilar[toplamBoyut];
    int w = 0;
    for ( int i = 0; i < devreKapiSayisi; i++, w++)
    {
        kapilar[w] = devreKapilar[i];
    }

    for ( int i = 0; i < baskaDosyaKapiSayisi; i++, w++)
    {
        kapilar[w] = baskaDosyaKapilar[i];
    }

    for ( int i = 0; i < toplamBoyut; i++)
    {
        for ( int j = 0; j < toplamBoyut; j++)
        {
            for ( int k = 0; k < kapilar[j].girisSayisi; k++)
            {
                if (kapilar[i].cikis == kapilar[j].girisler[k])
                {
                    kapilar[j].girisDegerleri[k] = kapilar[i].cikisDegeri;
                }
            }

        }
    }

    /// kapilari gecikme suresine gore siraya koymak

    for (int i = (toplamBoyut - 1); i >= 0; i--)
    {
        for (int j = 1; j <= i; j++)
        {
            if (kapilar[j-1].kapiGecikmesi > kapilar[j].kapiGecikmesi)
            {
                struct kapi temp = kapilar[j-1];
                kapilar[j-1] = kapilar[j];
                kapilar[j] = temp;
            }
        }
    }

    for ( int i = 0; i < toplamBoyut; i++)
    {
        if(kapilar[i].cikis == anaCikis)
        {
            for ( int j = i+1; j < toplamBoyut; j++)
            {
                if ( kapilar[i].kapiGecikmesi == kapilar[j].kapiGecikmesi)
                {
                    struct kapi temp = kapilar[j];
                    kapilar[j] = kapilar[i];
                    kapilar[i] = temp;
                }
            }
        }
    }
    /// kapi gecikmesi ayni olanlarin tespit edilmesi

    int anaCikisIndeks = -1;
    bool anaCikisEsitlik = false;
    struct kapi anaCikisKapisi;
    int sayac = 0;
    int eskiDeger;
    for ( int i = 0; i < toplamBoyut; i++)
    {
        if ( kapilar[i].cikis == anaCikis)
            anaCikisKapisi = kapilar[i];

        if ( kapilar[i].kapiGecikmesi == anaCikisKapisi.kapiGecikmesi)
        {
            anaCikisEsitlik = true;
            sayac++;
        }
    }

    struct kapi ziyaretEdildiMi[toplamBoyut];
    /// hesaplanan kapinin cikis degeri -1 yapilir ve bu ziyaret edildigi anlamina gelir
    for ( int i = 0; i < toplamBoyut; i++)
    {
        ziyaretEdildiMi[i] = kapilar[i];
    }

    bool cikisiEtkiliyorMu = false;
    int sonuc;
    int boyut = -1;
    int sayac2 = 0;
    bool durum = false;
    int sure = 0;

    for ( int i = 0; i < toplamBoyut; i++)
    {
        for ( int j = 0; j < toplamBoyut; j++)
        {
            for ( int k = 0; k < kapilar[j].girisSayisi; k++)
            {
                if ( kapilar[i].cikis == kapilar[j].girisler[k] /*&& kapilar[i].girisler[j] != anaCikisKapisi.cikis*/)
                {
                    cikisiEtkiliyorMu = true;
                    anaCikisKapisi = kapilar[j];
                    break;
                }
            }
        }

        if ( cikisiEtkiliyorMu == true )
        {
            if (ziyaretEdildiMi[i].cikisDegeri == -1)
            {
                continue;
            }
            else
            {
                fprintf(dosya,"   %d ns.", sure);

                if ( kapilar[i].kapiGecikmesi <= anaCikisKapisi.kapiGecikmesi && ziyaretEdildiMi[i].cikisDegeri != -1)
                {
                    if ( kapilar[i].kapiGecikmesi != anaCikisKapisi.kapiGecikmesi)
                        sure += kapilar[i].kapiGecikmesi;
                    else
                        sure += kapilar[i].kapiGecikmesi;


                    //printf("\n %d ns.", sure);
                    eskiDeger = kapilar[i].cikisDegeri;
                    sonuc = kapiHesapla(kapilar[i].kapiTipi, kapilar[i].girisDegerleri, kapilar[i].girisSayisi);
                    kapilar[i].cikisDegeri = kapiHesapla(kapilar[i].kapiTipi, kapilar[i].girisDegerleri, kapilar[i].girisSayisi);

                    //sure += kapilar[i].kapiGecikmesi;
                    printf("\n %d ns", sure);
                    printf("\n %c : %d -> %d", kapilar[i].cikis, eskiDeger, sonuc);
                    fprintf(dosya,"   %c : %c -> %d",kapilar[i].cikis, eskiDeger, sonuc );

                    degerGuncelle(&kapilar, toplamBoyut, kapilar[i].cikis, sonuc);

                    /// ziyaret edildi
                    ziyaretEdildiMi[i].cikisDegeri = -1;
                }

                else if (kapilar[i].kapiGecikmesi > anaCikisKapisi.kapiGecikmesi && ziyaretEdildiMi[i].cikisDegeri != -1)
                {
                    sure += kapilar[i].kapiGecikmesi;

                    eskiDeger = kapilar[i].cikisDegeri;
                    sonuc = kapiHesapla(kapilar[i].kapiTipi, kapilar[i].girisDegerleri, kapilar[i].girisSayisi);
                    kapilar[i].cikisDegeri = kapiHesapla(kapilar[i].kapiTipi, kapilar[i].girisDegerleri, kapilar[i].girisSayisi);
                    printf("\n %d ns", sure);
                    printf("\n %c : %d -> %d", kapilar[i].cikis, eskiDeger, sonuc);
                    fprintf(dosya,"   %c : %c -> %d",kapilar[i].cikis, eskiDeger, sonuc);

                    degerGuncelle(&kapilar, toplamBoyut, kapilar[i].cikis, sonuc);
                    ziyaretEdildiMi[i].cikisDegeri = -1;
                }
            }
        }
        else
        {

            sure += kapilar[i].kapiGecikmesi;
            printf("\n %d ns", sure);
            fprintf(dosya,"   %d ns.", sure);
            eskiDeger = kapilar[i].cikisDegeri;
            sonuc = kapiHesapla(kapilar[i].kapiTipi, kapilar[i].girisDegerleri, kapilar[i].girisSayisi);
            kapilar[i].cikisDegeri = kapiHesapla(kapilar[i].kapiTipi, kapilar[i].girisDegerleri, kapilar[i].girisSayisi);

            printf("\n %c : %d -> %d", kapilar[i].cikis, eskiDeger, sonuc);
            fprintf(dosya,"   %c : %c -> %d",kapilar[i].cikis, eskiDeger, sonuc );

            degerGuncelle(&kapilar, toplamBoyut, kapilar[i].cikis, sonuc);

            ziyaretEdildiMi[i].cikisDegeri = -1;

        }
    }

    eskiDeger = anaCikisKapisi.cikisDegeri;
    sonuc = kapiHesapla(anaCikisKapisi.kapiTipi, anaCikisKapisi.girisDegerleri, anaCikisKapisi.girisSayisi);

    sure += anaCikisKapisi.kapiGecikmesi;
    printf("\n %d ns", sure);
    printf("\n %c : %d -> %d", anaCikisKapisi.cikis, eskiDeger, sonuc);
    fprintf(dosya,"   %c : %c -> %d",anaCikisKapisi.cikis, eskiDeger, sonuc);
    degerGuncelle(&kapilar, toplamBoyut,anaCikisKapisi.cikis, sonuc);
    cikisiEtkiliyorMu = false;


}


void hKomutunuCalistir(struct kapi kapilar[], int kapiSayisi, char *degisecekler, int degiseceklerinSayisi, char anaCikis, FILE *dosya2)
{

    char tut;
    int degertut1 = -1;
    int degertut2;

    for ( int i = 0; i < kapiSayisi; i++)
    {
        for ( int j = 0; j < kapilar[i].girisSayisi; j++)
        {
            for ( int k = 0; k < degiseceklerinSayisi; k++)
            {
                if (degisecekler[k] == kapilar[i].girisler[j])
                {
                    tut = kapilar[i].girisler[j];
                    degertut1 = kapilar[i].girisDegerleri[j];
                    kapilar[i].girisDegerleri[j] = 1;
                    degertut2 = kapilar[i].girisDegerleri[j];

                    fclose(dosya2);
                    dosya2 = fopen("loglar.txt","a");


                    fprintf(dosya2, "   %c : %d->%d",tut, degertut1, kapilar[i].girisDegerleri[j]);

                }

                else if ( degisecekler[k] == anaCikis)
                {
                    for ( int z = 0; z < kapiSayisi; z++)
                    {
                        if ( kapilar[z].cikis == anaCikis)
                        {
                            tut = kapilar[z].cikis;
                            degertut1 = kapilar[z].cikisDegeri;
                            kapilar[z].cikisDegeri = 1;
                            degertut2 = kapilar[z].cikisDegeri ;

                            fclose(dosya2);
                            dosya2 = fopen("loglar.txt","a");


                            fprintf(dosya2, "   %c : %d->%d",tut, degertut1, kapilar[z].cikisDegeri);
                        }
                    }
                }

            }

        }
    }

    if ( degertut1 != -1)
        printf("\n\n%c : %d->%d",tut, degertut1, degertut2);

}


void LKomutunuCalistir(struct kapi kapilar[], int kapiSayisi, char degisecekler[], int degiseceklerinSayisi, char anaCikis, FILE *dosya2)
{
    char tut;
    int degertut1 = -1;
    int degertut2;

    for ( int i = 0; i < kapiSayisi; i++)
    {
        for ( int j = 0; j < kapilar[i].girisSayisi; j++)
        {
            for ( int k = 0; k < degiseceklerinSayisi; k++)
            {
                if (degisecekler[k] == kapilar[i].girisler[j])
                {
                    tut = kapilar[i].girisler[j];
                    degertut1 = kapilar[i].girisDegerleri[j];
                    kapilar[i].girisDegerleri[j] = 0;
                    degertut2 = kapilar[i].girisDegerleri[j];

                    fclose(dosya2);
                    dosya2 = fopen("loglar.txt","a");


                    fprintf(dosya2, "   %c : %d->%d",tut, degertut1, kapilar[i].girisDegerleri[j]);

                }

                else if ( degisecekler[k] == anaCikis)
                {
                    for ( int z = 0; z < kapiSayisi; z++)
                    {
                        if ( kapilar[z].cikis == anaCikis)
                        {
                            tut = kapilar[z].cikis;
                            degertut1 = kapilar[z].cikisDegeri;
                            kapilar[z].cikisDegeri = 0;
                            degertut2 = kapilar[z].cikisDegeri ;

                            //printf("\n\n%c : %d->%d",tut, degertut, kapilar[z].cikisDegeri);

                            fclose(dosya2);
                            dosya2 = fopen("loglar.txt","a");


                            fprintf(dosya2, "   %c : %d->%d",tut, degertut1, kapilar[z].cikisDegeri);
                        }
                    }
                }

            }

        }
    }

    if ( degertut1 != -1)
        printf("\n\n%c : %d->%d",tut, degertut1, degertut2);
}


void butunDegerleriGoster(struct kapi devreKapilar[], int devreKapiSayisi, struct kapi baskaDosyaKapilar[], int baskaDosyaKapiSayisi, char devreAnaCikisi, int anaCikisDegeri, FILE *dosya)
{



    int toplamBoyut = 0;

    for ( int i = 0; i < devreKapiSayisi; i++)
    {
        toplamBoyut += devreKapilar[i].girisSayisi;
    }

    for ( int i = 0; i < baskaDosyaKapiSayisi; i++)
    {
        toplamBoyut += baskaDosyaKapilar[i].girisSayisi;
    }

    char temp[toplamBoyut];

    int k = 0;

    for ( int i = 0; i < devreKapiSayisi; i++)
    {
        for (int j = 0; j < devreKapilar[i].girisSayisi; j++)
        {
            temp[k] = devreKapilar[i].girisler[j];
            k++;
        }
    }

    for ( int i = 0; i < baskaDosyaKapiSayisi; i++)
    {
        for (int j = 0; j < baskaDosyaKapilar[i].girisSayisi; j++)
        {
            temp[k] = baskaDosyaKapilar[i].girisler[j];
            k++;
        }
    }

    /// ayni olanlar secilip baska diziye aktarilacak;

    for ( int i = 0; i < toplamBoyut; i++)
    {
        char tut = temp[i];
        for ( int j = 0; j < toplamBoyut; j++)
        {
            if ( j == i)
                continue;
            if ( tut == temp[j])
            {
                temp[j] = '#';
            }
        }
    }

    int sayac = 0;

    for ( int i = 0; i < toplamBoyut; i++)
    {
        if ( temp[i] != '#')
        {
            sayac++;
        }
    }

    char asilGirisler[sayac];
    int w = 0;

    for ( int i = 0; i < sayac; i++)
    {
        if ( temp[i] != '#')
        {
            asilGirisler[w] = temp[i];
            w++;
        }
    }

    char son[sayac];
    son[sayac] = '\0';
    for ( int i = 0; i < sayac; i++)
    {
        son[i] = asilGirisler[i];
    }


    bool yazilanlar[sayac];

    for ( int i = 0; i < sayac; i++)
        yazilanlar[i] = false;

    for ( int i = 0; i < sayac; i++ )
    {
        for ( int j = 0; j < devreKapiSayisi; j++)
        {
            for ( int k = 0; k < devreKapilar[j].girisSayisi; k++)
            {
                if ( devreKapilar[j].girisler[k] == son[i] && yazilanlar[i] != true)
                {
                    yazilanlar[i] = true;
                    printf("\n %c : %d", son[i], devreKapilar[j].girisDegerleri[k]);
                    fprintf(dosya, "   %c : %d", son[i], devreKapilar[j].girisDegerleri[k]);
                }
            }
        }
    }

    for ( int i = 0; i < sayac; i++ )
    {
        for ( int j = 0; j < baskaDosyaKapiSayisi; j++)
        {
            for ( int k = 0; k < baskaDosyaKapilar[j].girisSayisi; k++)
            {
                if ( baskaDosyaKapilar[j].girisler[k] == son[i] && yazilanlar[i] != true)
                {
                    yazilanlar[i] = true;
                    printf("\n %c : %d", son[i], baskaDosyaKapilar[j].girisDegerleri[k]);
                    fprintf(dosya, "   %c : %d", son[i], baskaDosyaKapilar[j].girisDegerleri[k]);
                }
            }
        }
    }

}

void degerGoster(struct kapi devreKapilar[], int devreKapiSayisi, struct kapi baskaDosyaKapilar[], int baskaDosyaKapiSayisi,
                 char kullaniciKomutu[], int kullaniciKomutuBoyutu, FILE *dosya)
{

    bool yazilanlar[kullaniciKomutuBoyutu];

    for ( int i = 0; i < kullaniciKomutuBoyutu; i++)
        yazilanlar[i] = false;


    for ( int i = 0; i < kullaniciKomutuBoyutu; i++ )
    {
        for ( int j = 0; j < devreKapiSayisi; j++)
        {
            for ( int k = 0; k < devreKapilar[j].girisSayisi; k++)
            {
                if ( devreKapilar[j].girisler[k] == kullaniciKomutu[i] && yazilanlar[i] != true)
                {
                    yazilanlar[i] = true;
                    printf("\n %c : %d", kullaniciKomutu[i], devreKapilar[j].girisDegerleri[k]);
                    fprintf(dosya, "   %c : %d", kullaniciKomutu[i], devreKapilar[j].girisDegerleri[k]);
                }
                else if (kullaniciKomutu[i] == devreKapilar[j].cikis && yazilanlar[i] != true)
                {
                    yazilanlar[i] = true;
                    printf("\n %c : %d", kullaniciKomutu[i], devreKapilar[j].cikisDegeri);
                    fprintf(dosya, "   %c : %d", kullaniciKomutu[i], devreKapilar[j].cikisDegeri);

                }
            }
        }
    }
    for ( int i = 0; i < kullaniciKomutuBoyutu; i++ )
    {
        for ( int j = 0; j < baskaDosyaKapiSayisi; j++)
        {
            for ( int k = 0; k < baskaDosyaKapilar[j].girisSayisi; k++)
            {
                if ( baskaDosyaKapilar[j].girisler[k] == kullaniciKomutu[i] && yazilanlar[i] != true)
                {
                    yazilanlar[i] = true;
                    printf("\n %c : %d", kullaniciKomutu[i], baskaDosyaKapilar[j].girisDegerleri[k]);
                    fprintf(dosya, "   %c : %d", kullaniciKomutu[i], baskaDosyaKapilar[j].girisDegerleri[k]);
                }
                else if (kullaniciKomutu[i] == baskaDosyaKapilar[j].cikis && yazilanlar[i] != true)
                {
                    yazilanlar[i] = true;
                    printf("\n %c : %d", kullaniciKomutu[i], baskaDosyaKapilar[j].cikisDegeri);
                    fprintf(dosya, "   %c : %d", kullaniciKomutu[i], baskaDosyaKapilar[j].cikisDegeri);

                }
            }
        }
    }


}


int komutDosyasiBoyutu()
{
    int sayac = 0;
    FILE *dosya;
    dosya = fopen("komut1.txt","r");

    char ch;
    while ( ch != EOF)
    {
        ch = getc(dosya);
        sayac++;
    }

    fclose(dosya);
    return sayac;

}

int main()
{

    time_t timer;
    struct tm* tm_info;
    char day[3];
    char month[3];
    char year[5];
    time(&timer);
    tm_info = localtime(&timer);

    strftime(day, 3, "%d", tm_info);
    strftime(month, 3, "%m", tm_info);
    strftime(year, 5, "%Y", tm_info);

    FILE *dosya;
    dosya = fopen("loglar.txt", "a");

    char okunan[50];
devre:

    printf("> "); /// DEVRENIN YUKLENMESI
    scanf(" %[^\n]", &okunan);
    if (okunan[0] == 'y' || okunan[0] == 'Y')
    {



        int uzunluk;
        for (uzunluk = 0; okunan[uzunluk] != '\0'; uzunluk++) {}

        char dosyaAdi[uzunluk-2];

        for ( int i = 0; i < uzunluk; i++)
        {
            dosyaAdi[i] = okunan[i+2];
        }
        printf("\n(!) Devre projeye dahil edildi.\n");


        //fclose(dosya);
        //dosya = fopen("loglar.txt","a");

        fprintf(dosya, "\n");

        fprintf(dosya, "%s/%s/%s-", month, day, year);
        fprintf(dosya, "%d:%d:%d",
           tm_info->tm_hour,
           tm_info->tm_min,
           tm_info->tm_sec);


        fprintf(dosya, "  %s  %s %s\n",okunan,dosyaAdi," yuklendi.");

        /// DEGER.TXT DOSYASI

        char okunan2[50];

deger:

        printf("\n\n>"); /// DEGERLERIN YUKLENMESI
        scanf(" %[^\n]", &okunan2);

        if (okunan2[0] == 'i' || okunan2[0] == 'I')
        {
            for ( uzunluk = 0; okunan2[uzunluk] != '\0'; uzunluk++) {}

            char degerDosyasi[uzunluk-2]; /// okunan deger de i deger.txt gibi olacağı için i ve bir bosluk kadar sonrasina otelendi

            for ( int i = 0; i < uzunluk; i++)
            {
                degerDosyasi[i] = okunan2[i+2];
            }
            printf("\n(!) Degerler devreye yuklendi.\n");

            fprintf(dosya, "\n");

            fprintf(dosya, "%s/%s/%s-", month, day, year);
            fprintf(dosya, "%d:%d:%d",
            tm_info->tm_hour,
            tm_info->tm_min,
            tm_info->tm_sec);


            fprintf(dosya, "  %s  %s %s\n",okunan2,degerDosyasi," yuklendi.");


            int ikinciDosyaBoyut = baskaDosyaBoyut(dosyaAdi);
            char temp[ikinciDosyaBoyut];

            baskaDosya(dosyaAdi, temp);

            char ikinciDosya[ikinciDosyaBoyut];

            for (int i = 0; i < ikinciDosyaBoyut; i++)
            {
                ikinciDosya[i] = temp[i];
            }

            /*
            *********************************************************************************************************************************************************
                                    YAZDIR FONKSIYONUNDAN ALINANLAR
            *********************************************************************************************************************************************************
            */
            int devreBoyut, baskaDosyaUzunluk, toplamBoyut;
            devreBoyut = devreninAnaGirisSayisi(dosyaAdi);
            baskaDosyaUzunluk = devreninAnaGirisSayisi(ikinciDosya);

            toplamBoyut = devreBoyut + baskaDosyaUzunluk;

            char devreGirisler[devreBoyut];
            char baskaDosyaGirisler[baskaDosyaUzunluk];
            char toplamGirisler[toplamBoyut];

            devreninAnaGirisleri(dosyaAdi, devreGirisler, devreBoyut);
            devreninAnaGirisleri(ikinciDosya, baskaDosyaGirisler, baskaDosyaUzunluk);
            int i=0,j=0,k = 0;
            bool durum = false;
            while ( i < toplamBoyut)
            {
                while(j < devreBoyut)
                {
                    toplamGirisler[i] = devreGirisler[j];
                    i++;
                    j++;
                }
                while(k < baskaDosyaUzunluk)
                {
                    toplamGirisler[i] = baskaDosyaGirisler[k];
                    i++;
                    k++;
                }
            }

            char devreAnaCikis = devreninAnaCikisi(dosyaAdi);
            char baskaDosyaAnaCikis = devreninAnaCikisi(ikinciDosya);

            int devreKapiSayisi = devredekiKapiSayisi(dosyaAdi);
            int baskaDosyaKapiSayisi = devredekiKapiSayisi(ikinciDosya);
            struct kapi devreKapilar[devreKapiSayisi];
            struct kapi baskaDosyaKapilar[baskaDosyaKapiSayisi];


            kapilarVeBilgileri(dosyaAdi, devreKapilar, devreKapiSayisi);
            kapilarVeBilgileri(ikinciDosya, baskaDosyaKapilar, baskaDosyaKapiSayisi);

            int *devreAnaCikisDegeri;
            int *baskaDosyaAnaCikisDegeri;

            /// BURADA KALDIM
            ilkDegerleriBelirle(degerDosyasi, &baskaDosyaKapilar, baskaDosyaKapiSayisi, baskaDosyaAnaCikis, &baskaDosyaAnaCikisDegeri);

            printf("\n");

            ilkDegerleriBelirle(degerDosyasi, &devreKapilar, devreKapiSayisi, devreAnaCikis, &devreAnaCikisDegeri);

            degerDuzenle(devreKapilar, devreKapiSayisi); /// CALISIYOR
            degerDuzenle(baskaDosyaKapilar, baskaDosyaKapiSayisi); /// CALISIYOR

            printf("\n");
            /*
            *********************************************************************************************************************************************************
                                    YAZDIR FONKSIYONUNDAN ALINANLAR BITIS
            *********************************************************************************************************************************************************
            */

            /// H KOMUTU

            char komut[50];
komutAl:


            printf("\n\n>"); /// DEGERLERIN YUKLENMESI
            scanf(" %[^\n]", &komut);


            //fprintf(dosya, "\n");


            fprintf(dosya, "\n%s/%s/%s-", month, day, year);
            fprintf(dosya, "%d:%d:%d",
            tm_info->tm_hour,
            tm_info->tm_min,
            tm_info->tm_sec);


            fprintf(dosya, "  %s\n",komut);

            if ( komut[0] == 'H' || komut[0] == 'h')
            {


                for(uzunluk = 0; komut[uzunluk] != '\0'; uzunluk++) {}

                char * kullaniciKomutu = (char*)malloc(sizeof(char)*(uzunluk-2));

                for ( int i = 0; i < uzunluk; i++)
                {
                    kullaniciKomutu[i] = komut[i+2];
                }

                int sayac = 0;
                for ( sayac = 0; kullaniciKomutu[sayac] != '\0'; sayac++) {}


                hKomutunuCalistir(&baskaDosyaKapilar, baskaDosyaKapiSayisi, kullaniciKomutu, sayac, baskaDosyaAnaCikis,dosya);

                hKomutunuCalistir(&devreKapilar, devreKapiSayisi, kullaniciKomutu, sayac, devreAnaCikis,dosya);



                goto komutAl;
            }

            else if ( komut[0] == 'L' || komut[0] == 'l')
            {

                for(uzunluk = 0; komut[uzunluk] != '\0'; uzunluk++) {}

                char * kullaniciKomutu = (char*)malloc(sizeof(char)*(uzunluk-2));

                for ( int i = 0; i < uzunluk; i++)
                {
                    kullaniciKomutu[i] = komut[i+2];
                }

                int sayac = 0;
                for ( sayac = 0; kullaniciKomutu[sayac] != '\0'; sayac++) {}

                LKomutunuCalistir(&baskaDosyaKapilar, baskaDosyaKapiSayisi, kullaniciKomutu, sayac, baskaDosyaAnaCikis,dosya);

                LKomutunuCalistir(&devreKapilar, devreKapiSayisi, kullaniciKomutu, sayac, devreAnaCikis,dosya);

                goto komutAl;
            }

            else if ( komut[0] == 'G' || komut[0] == 'g')
            {

                if (komut[1] == '*')
                {
                    butunDegerleriGoster(devreKapilar, devreKapiSayisi, baskaDosyaKapilar, baskaDosyaKapiSayisi, devreAnaCikis, &devreAnaCikisDegeri, dosya);
                    goto komutAl;
                }
                else
                {
                    for(uzunluk = 0; komut[uzunluk] != '\0'; uzunluk++) {}

                    char * kullaniciKomutu = (char*)malloc(sizeof(char)*(uzunluk-2));
                    for ( int i = 0; i < uzunluk; i++)
                    {
                        kullaniciKomutu[i] = komut[i+2];
                    }

                    int sayac = 0;
                    for ( sayac = 0; kullaniciKomutu[sayac] != '\0'; sayac++) {}

                    degerGoster(devreKapilar, devreKapiSayisi, baskaDosyaKapilar, baskaDosyaKapiSayisi, kullaniciKomutu, sayac,dosya);

                    goto komutAl;
                }
                fprintf(dosya, "\n");
            }

            else if ( komut[0] == 'S' || komut[0] == 's')
            {
                hesapla(devreKapilar, devreKapiSayisi, baskaDosyaKapilar, baskaDosyaKapiSayisi, devreAnaCikis,dosya);


                goto komutAl;
            }

            else if(komut[0] == 'K' || komut[0] == 'k')
            {
                int deger = KKomutu(dosya);

                if ( deger == 1 )
                {
                    goto komutAl;
                }
                else if ( deger == 0)
                {
                    goto bitis;
                }

            }
            else if ( komut[0] == 'C' || komut[0] == 'c')
            {


            fprintf(dosya, "   %s","program sonlandirildi.");

                goto bitis;
            }

        }
        else
        {
            printf("\nDevre degerleri yuklenmesi gerekmektedir. \"I <deger_dosyasi.txt>\" komutunu kullanin.");
            goto deger;
        }
    }


    else
    {
        printf("\nOncelikle devre olusturulmali. \"Y <devre_dosyasi.txt>\" komutunu kullanin");
        goto devre;
    }

bitis:


    fclose(dosya);
    return 0;
}

int KKomutu(FILE *dosya2)
{


    int deger = 1;

    FILE *dosya;
    dosya = fopen("komut1.txt","r");

    char ch;
    int k = 0;
    while ( ch != EOF)
    {
        if (ch == 'y' || ch == 'Y')
        {

            char *dosyaAdi = (char*)malloc(sizeof(char)*9);

            ch = getc(dosya);
            ch = getc(dosya);
            while ( ch != 10)
            {
                dosyaAdi[k] = ch;
                k++;
                ch = getc(dosya);
            }

            printf("\n(!) Devre projeye dahil edildi.\n");

            /// DEGER.TXT DOSYASI

            ch = getc(dosya);
            ch = getc(dosya);

            if (ch == 'i' || ch == 'I')
            {
                ch = getc(dosya);
                ch = getc(dosya);

                char *degerDosyasi = (char*)malloc(sizeof(char)*9);
                k = 0;
                while ( ch != 10)
                {
                    degerDosyasi[k] = ch;
                    k++;
                    ch = getc(dosya);
                }

                printf("\n(!) Degerler devreye yuklendi.\n");


                dosyaAdi[9] = '\0';
                int ikinciDosyaBoyut = baskaDosyaBoyut(dosyaAdi);
                char temp[ikinciDosyaBoyut];

                baskaDosya(dosyaAdi, temp);

                char ikinciDosya[ikinciDosyaBoyut];

                for (int i = 0; i < ikinciDosyaBoyut; i++)
                {
                    ikinciDosya[i] = temp[i];
                }


                /*
                *********************************************************************************************************************************************************
                                        YAZDIR FONKSIYONUNDAN ALINANLAR
                *********************************************************************************************************************************************************
                */
                int devreBoyut, baskaDosyaUzunluk, toplamBoyut;
                devreBoyut = devreninAnaGirisSayisi(dosyaAdi);
                baskaDosyaUzunluk = devreninAnaGirisSayisi(ikinciDosya);

                toplamBoyut = devreBoyut + baskaDosyaUzunluk;

                char devreGirisler[devreBoyut];
                char baskaDosyaGirisler[baskaDosyaUzunluk];
                char toplamGirisler[toplamBoyut];

                devreninAnaGirisleri(dosyaAdi, devreGirisler, devreBoyut);
                devreninAnaGirisleri(ikinciDosya, baskaDosyaGirisler, baskaDosyaUzunluk);
                int i=0,j=0,k = 0;
                bool durum = false;
                while ( i < toplamBoyut)
                {
                    while(j < devreBoyut)
                    {
                        toplamGirisler[i] = devreGirisler[j];
                        i++;
                        j++;
                    }
                    while(k < baskaDosyaUzunluk)
                    {
                        toplamGirisler[i] = baskaDosyaGirisler[k];
                        i++;
                        k++;
                    }
                }

                char devreAnaCikis = devreninAnaCikisi(dosyaAdi);
                char baskaDosyaAnaCikis = devreninAnaCikisi(ikinciDosya);

                int devreKapiSayisi = devredekiKapiSayisi(dosyaAdi);
                int baskaDosyaKapiSayisi = devredekiKapiSayisi(ikinciDosya);
                struct kapi devreKapilar[devreKapiSayisi];
                struct kapi baskaDosyaKapilar[baskaDosyaKapiSayisi];


                kapilarVeBilgileri(dosyaAdi, devreKapilar, devreKapiSayisi);
                kapilarVeBilgileri(ikinciDosya, baskaDosyaKapilar, baskaDosyaKapiSayisi);

                int *devreAnaCikisDegeri;
                int *baskaDosyaAnaCikisDegeri;

                ilkDegerleriBelirle(degerDosyasi, &baskaDosyaKapilar, baskaDosyaKapiSayisi, baskaDosyaAnaCikis, &baskaDosyaAnaCikisDegeri);
                printf("\n");
                ilkDegerleriBelirle(degerDosyasi, &devreKapilar, devreKapiSayisi, devreAnaCikis, &devreAnaCikisDegeri);

                degerDuzenle(devreKapilar, devreKapiSayisi); /// CALISIYOR
                degerDuzenle(baskaDosyaKapilar, baskaDosyaKapiSayisi); /// CALISIYOR

                while (ch != EOF)
                {

                    if ( ch == 'H' || ch == 'h')
                    {


                        char tempDegisecekler[15];
                        k = 0;
                        ch = getc(dosya);
                        while ( ch != 10)
                        {
                            if(ch != (char)9)
                            {
                                tempDegisecekler[k] = ch;
                                k++;
                            }
                            ch = getc(dosya);
                        }

                        char degisecekler[k];
                        for (i = 0; i < k; i++)
                        {
                            degisecekler[i] = tempDegisecekler[i];
                        }
                        degisecekler[k] = '\0';

                        hKomutunuCalistir(&baskaDosyaKapilar, baskaDosyaKapiSayisi, tempDegisecekler, k, baskaDosyaAnaCikis,dosya2);
                        hKomutunuCalistir(&devreKapilar, devreKapiSayisi, tempDegisecekler, k, devreAnaCikis,dosya2);


                    }

                    else if ( ch == 'L' || ch == 'l')
                    {
                        char tempDegisecekler[15];
                        k = 0;
                        ch = getc(dosya);
                        while ( ch != 10)
                        {
                            if(ch != (char)9)
                            {
                                tempDegisecekler[k] = ch;
                                k++;
                            }
                            ch = getc(dosya);
                        }

                        char degisecekler[k];
                        for (i = 0; i < k; i++)
                        {
                            degisecekler[i] = tempDegisecekler[i];
                        }
                        degisecekler[k] = '\0';

                        LKomutunuCalistir(&baskaDosyaKapilar, baskaDosyaKapiSayisi, degisecekler, k, baskaDosyaAnaCikis,dosya2);
                        LKomutunuCalistir(&devreKapilar, devreKapiSayisi, degisecekler, k, devreAnaCikis,dosya2);
                    }

                    else if ( ch == 'G' || ch == 'g')
                    {

                        char *kkomut = (char*)malloc(sizeof(char)*10);
                        k = 0;
                        while ( ch != (char)10)
                        {
                            kkomut[k] = ch;
                            ch = getc(dosya);
                            k++;
                        }

                        if (kkomut[1] == '*')
                        {
                            butunDegerleriGoster(devreKapilar, devreKapiSayisi, baskaDosyaKapilar, baskaDosyaKapiSayisi, devreAnaCikis, &devreAnaCikisDegeri, dosya2);
                        }
                        else
                        {
                            printf("\n");
                            k = 1;
                            char gosterilecek[1];
                            gosterilecek[0] = kkomut[2];
                            degerGoster(devreKapilar, devreKapiSayisi, baskaDosyaKapilar, baskaDosyaKapiSayisi, gosterilecek, k,dosya2);
                        }
                        fprintf(dosya2, "\n");
                    }

                    else if ( ch == 'S' || ch == 's')
                    {
                        hesapla(devreKapilar, devreKapiSayisi, baskaDosyaKapilar, baskaDosyaKapiSayisi, devreAnaCikis,dosya2);
                    }
                    else if ( ch == 'C' || ch == 'c')
                    {
                        deger = 0;
                    }
                    ch = getc(dosya);
                }
            }
        }
        ch = getc(dosya);
    }
    fclose(dosya);
    return deger;
}


void dosyaAc(char dosyaAdi[])
{
    FILE *dosya;
    dosya = fopen(dosyaAdi, "r");

    char ch;
    while(ch != EOF)
    {
        ch = getc(dosya);
    }

    fclose(dosya);

    int giris_sayisi = 0;
    giris_sayisi = devredekiKapiSayisi(dosyaAdi);

    char devrenin_ana_cikisi = devreninAnaCikisi(dosyaAdi);
}

int baskaDosyaBoyut(char dosyaAdi[])
{
    FILE *dosya;
    dosya = fopen(dosyaAdi,"r");
    char ch;

    while(ch != (char)9)
    {
        ch = getc(dosya);
    }

    char temp[15];

    int i = 0;

    while (ch != 'g')
    {
        ch = getc(dosya);
        temp[i] =  ch;
        i++;
    }

    for(i = 0; temp[i] != (char)10; i++) {}

    int uzunluk = i;

    return i;
}

void baskaDosya(char dosyaAdi[], char ikinciDosya[])
{
    FILE *dosya;
    dosya = fopen(dosyaAdi,"r");
    char ch;

    while(ch != (char)9)
    {
        ch = getc(dosya);
    }

    char temp[15];

    int i = 0;

    while (ch != 'g')
    {
        ch = getc(dosya);
        temp[i] =  ch;
        i++;
    }

    for(i = 0; temp[i] != (char)10; i++) {}

    int uzunluk = i;

    for ( i = 0; i < uzunluk; i++ )
    {
        ikinciDosya[i] = temp[i];
    }

    fclose(dosya);
}

void degerDuzenle(struct kapi kapilar[], int boyut)
{
    /// farkli kapilara ayni girdi oldugu zaman, degerin giris yaptigi ilk kapida girdinin degeri saklanabilirken
    /// sonradan gelen kapilarda bu degerler saklanamadigi icin ayni girdiye sahip kapilarin tespit edilip ilk saklanan kapidaki degeri
    /// ayni oldugu tespit edilen kapilarin girdi degerlerine kopyalanmasini gerceklestirir.
    for ( int i = 0; i < boyut; i++ )
    {
        for(int j = 0; j < kapilar[i].girisSayisi; j++)
        {
            char tut = kapilar[i].girisler[j];
            for ( int k = 0; k < boyut; k++)
            {
                for ( int w = 0; w < kapilar[k].girisSayisi; w++)
                {
                    if( tut == kapilar[k].girisler[w])
                    {
                        kapilar[k].girisDegerleri[w] = kapilar[i].girisDegerleri[j];
                    }
                }
            }
        }
    }
}

void degerDegistir(int *a, int *b)
{
    *a = *b;
}

void ilkDegerleriBelirle(char degerDosyasi[], struct kapi *kapilar, int boyut, char anaCikis, int * anaCikisDegeri)
{
    FILE *dosya;
    dosya = fopen(degerDosyasi,"r");

    char ch;
    int a;
    int cikisDegeriDegis;
    int degerBoyut;

    int **temp = (int**)malloc(sizeof(int*)*boyut);
    for ( int i = 0; i < boyut; i++)
    {
        degerBoyut = kapilar[i].girisSayisi;
        kapilar[i].girisDegerleri = (int*)malloc(sizeof(int)*degerBoyut);
        temp[i] = (int*)malloc(degerBoyut* sizeof(int));
    }

    /// degerlerin sifira initial edilmesi;
    for ( int i = 0; i < boyut; i++)
    {
        for ( int j = 0; j < kapilar[i].girisSayisi; j++)
        {
            kapilar[i].girisDegerleri[j] = 0;
            temp[i][j] = 0;
        }
    }

    struct tempKapi tempKapi[5];
    int p = 0;
    while (ch != EOF)
    {
        for ( int i = 0; i < boyut; i++ )
        {
            for ( int j = 0; j < kapilar[i].girisSayisi; j++)
            {
                /// ana cikisin degerinin alinmasi
                if ( ch == anaCikis)
                {
                    ch = getc(dosya);
                    ch = getc(dosya);
                    cikisDegeriDegis = ch - '0';
                    degerDegistir(&anaCikisDegeri, &cikisDegeriDegis);
                    kapilar[i].cikisDegeri = anaCikisDegeri;

                    for ( int z = 0; z < boyut; z++)
                    {
                        for ( int w = 0; w < kapilar[z].girisSayisi; w++)
                        {
                            if ( kapilar[z].cikis == anaCikis)
                            {
                                kapilar[z].cikisDegeri = anaCikisDegeri;
                            }
                        }
                    }
                }

                if ( ch == kapilar[i].cikis)
                {
                    tempKapi[p].cikis = ch;
                    ch = getc(dosya);
                    ch = getc(dosya);
                    a = ch - '0';
                    tempKapi[p].cikisDegeri = a;
                    p++;
                }

                if ( ch == kapilar[i].girisler[j])
                {
                    ch = getc(dosya);
                    ch = getc(dosya);

                    a = ch - '0';
                    temp[i][j] = a;
                }
            }
        }
        ch = getc(dosya);
    }

    for ( int i = 0; i < boyut; i++)
    {
        for ( int j = 0; j < kapilar[i].girisSayisi; j++)
        {
            kapilar[i].girisDegerleri[j] = temp[i][j];
        }
    }

    for ( int i = 0; i < p; i++)
    {
        for ( int j = 0; j < boyut; j++)
        {
            if ( kapilar[j].cikis == tempKapi[i].cikis)
            {
                kapilar[j].cikisDegeri = tempKapi[i].cikisDegeri;
            }
        }
    }

    fclose(dosya);
}


void kapilarVeBilgileri(char dosyaAdi[], struct kapi kapilar[], int kapiSayisi)
{
    /// devrenin kapi turlerini bulmak

    FILE *dosya;
    dosya = fopen(dosyaAdi, "r");

    int i = 0, sayac = 0;
    char ch;

    bool durum;
    durum = false;
    char kapi[4];
    kapi[0] = 'k';
    kapi[1] = 'a';
    kapi[2] = 'p';
    kapi[3] = 'i';
    char temp[4];
    char kapiTuru[4];
    char cikisKapisi;

    ch = getc(dosya);
    int j, k = 0;
    for (j = 0; j < kapiSayisi; j++)
    {
        while ( ch != EOF)
        {
            if(ch == '.')
            {
                ch = getc(dosya);
                for(i = 0; i < 4; i++)
                {
                    temp[i] = ch;
                    ch = getc(dosya);
                }
                for ( i = 0; i < 4; i++)
                {
                    if(temp[i] == kapi[i])
                    {
                        durum = true;
                    }
                    else
                    {
                        durum = false;
                        break;
                    }
                }

                if ( durum == true )
                {
                    ch = getc(dosya);
                    i = 0;
                    sayac = 0;
                    while ( (int) ch  != 9)
                    {
                        kapiTuru[i] = ch;
                        i++;
                        sayac++;
                        ch = getc(dosya);
                    }

                    kapilar[k].kapiTipi = (char*)malloc(sizeof(char)*sayac);
                    kapilar[k].kapiTipiUzunlugu = sayac;

                    for ( i = 0; i < sayac; i++)
                    {
                        kapilar[k].kapiTipi[i] = kapiTuru[i];
                    }

                    /// arada tab oldugu icin kapi giris sayisini bulmak icin bir karakter ilerletildi
                    ch = getc(dosya);

                    int ab = ch - '0';
                    kapilar[k].girisSayisi = ab;
                    /// arada tab oldugu icin kapi cikisini bulabilmek icin iki karakter ilerletildi
                    ch = getc(dosya);
                    ch = getc(dosya);

                    kapilar[k].cikis = ch;

                    /// kapi girislerini kayit edebilmek icin kapi giris sayisi kadar boyuta sahip dizi initial edilir
                    kapilar[k].girisler = (int*)malloc(sizeof(int)*kapilar[k].girisSayisi);

                    for ( int z = 0; z < kapilar[k].girisSayisi+1; z++)
                    {
                        /// her ikinci ilerlemede bir degere ulasildigi icin ikiser defa ilerletilip daha sonra degerler kayit edildi
                        for(int w = 0; w < 2; w++ )
                        {
                            ch = getc(dosya);
                        }
                        if(z == kapilar[k].girisSayisi)
                        {
                            kapilar[k].kapiGecikmesi = ch - '0';
                        }
                        else
                        {
                            kapilar[k].girisler[z] = ch;

                        }
                    }
                    k++;
                }
            }
            ch = getc(dosya);
        }
    }

    fclose(dosya);
}


void devreninAnaGirisleri (char dosyaAdi[], char devreGirisleri[], int boyut) /// kapilara islem yaptirabilmek icin gerekli
{
    char ch;
    int tabSayac = 0;

    FILE *dosya;
    dosya = fopen(dosyaAdi, "r");

    while ( ch != 'g')
    {
        ch = getc(dosya);
    }

    while (ch != (char)9 )
    {
        ch = getc(dosya);
        tabSayac++;
    }

    ///printf("tab sayac : %d", tabSayac);

    ///kaç adet deðiþken olduðunu bulmak için .giris keywordunden sonra # iþaretini görene kadar sayac ilerletildi ve degisken sayisinin
    /// (tabSayac-1)/2 oldugu bulundu.
    ///printf("degisken sayac : %d", degiskenSayac);

    int j = 0;
    char anaGirisler[(tabSayac-1)/2];

    for ( int i = 0; i < tabSayac; i++)
    {
        ch = getc(dosya);

        if ((int)ch != 9)
        {
            anaGirisler[j] = ch;
            j++;
        }
    }

    fclose(dosya);

    for ( int i = 0; i < boyut; i++)
    {
        devreGirisleri[i] = anaGirisler[i];
    }
}

int devredekiKapiSayisi(char dosyaAdi[]) /// struct dizisi olusturmak icin gerekli
{
    /// devrenin kapi sayisini bulmak

    FILE *dosya;
    dosya = fopen(dosyaAdi, "r");

    int i = 0, kapiSayisi = 0;
    char ch;

    bool durum;
    durum = false;
    char kapi[4];
    kapi[0] = 'k';
    kapi[1] = 'a';
    kapi[2] = 'p';
    kapi[3] = 'i';
    char temp[4];
    char cikisKapisi;

    ch = getc(dosya);
    while ( ch != EOF)
    {
        if(ch == '.')
        {
            ch = getc(dosya);
            for(i = 0; i < 4; i++)
            {
                temp[i] = ch;
                ch = getc(dosya);
            }
            for ( i = 0; i < 4; i++)
            {
                if(temp[i] == kapi[i])
                {
                    durum = true;
                }
                else
                {
                    durum = false;
                    break;
                }
            }
        }

        if ( durum == true )
        {
            kapiSayisi++;
            durum = false;
        }

        ch = getc(dosya);
    }

    fclose(dosya);

    return kapiSayisi;
}

char devreninAnaCikisi (char dosyaAdi[]) /// sonuc degerin ne oldugunu gosterebilmek icin gerekli
{
    /// devrenin ana cikisini bulmak

    FILE *dosya;
    dosya = fopen(dosyaAdi, "r");

    int i = 0;
    char ch;

    bool durum;
    durum = false;
    char cikis[5];
    cikis[0] = 'c';
    cikis[1] = 'i';
    cikis[2] = 'k';
    cikis[3] = 'i';
    cikis[4] = 's';
    char temp[5];
    char cikisKapisi;

    ch = getc(dosya);
    while ( ch != EOF)
    {
        if(ch == '.')
        {
            ch = getc(dosya);
            for(i = 0; i < 5; i++)
            {
                temp[i] = ch;
                ch = getc(dosya);
            }
        }
        for ( i = 0; i<5; i++)
        {
            if(temp[i] == cikis[i])
            {
                durum = true;
            }
            else
            {
                durum = false;
                break;
            }
        }

        if ( durum == true )
            break;


        ch = getc(dosya);
    }

    if ( durum == true )
    {

        while ( true )
        {
            ch = getc(dosya);
            if ( (int)ch != 9 )
            {
                cikisKapisi = ch;
                break;
            }
        }
    }

    fclose(dosya);

    return cikisKapisi;
}

int devreninAnaGirisSayisi (char dosyaAdi[]) /// kapilara ikiserli olarak islem yaptirabilmek icin gerekli
{
    char ch;
    int tabSayac = 0;

    FILE *dosya;
    dosya = fopen(dosyaAdi, "r");

    while ( ch != 'g')
    {
        ch = getc(dosya);
    }

    while (ch != (char)9)
    {
        ch = getc(dosya);
    }

    while (ch != '#')
    {
        ch = getc(dosya);
        tabSayac++;
    }

    return (tabSayac-1)/2;
}


int andKapisi(int p, int q)
{
    int sonuc = -1;

    if ( p == 0 && q == 0)
    {
        sonuc = 0;
    }
    else if ( (p == 0 && q == 1) || (p == 1 && q == 0) )
    {
        sonuc = 0;
    }
    else if ( p == 1 && q == 1 )
    {
        sonuc = 1;
    }

    return sonuc;
}

int orKapisi(int p, int q)
{
    int sonuc = -1;

    if ( p == 0 && q == 0)
    {
        sonuc = 0;
    }
    else
    {
        sonuc = 1;
    }

    return sonuc;
}

int xorKapisi(int p, int q)
{
    int sonuc = -1;

    if ( (p == 1 && q == 0) || (p == 0 && q == 1) )
    {
        sonuc = 1;
    }
    else
    {
        sonuc = 0;
    }

    return sonuc;
}

int notKapisi (int p)
{
    int sonuc = -1;

    if ( p == 0)
    {
        sonuc = 1;
    }
    else
    {
        sonuc = 0;
    }

    return sonuc;
}

int nandKapisi(int p, int q)
{
    int sonuc = -1;

    if ( (p == 0 && q == 0) || (p == 0 && q == 1) ||(p == 1 && q == 0) )
    {
        sonuc = 1;
    }
    else
    {
        sonuc = 0;
    }

    return sonuc;
}

int norKapisi(int p, int q)
{
    int sonuc = -1;

    if ( p == 0 && q == 0)
    {
        sonuc = 1;
    }
    else
    {
        sonuc = 0;
    }

    return sonuc;
}


