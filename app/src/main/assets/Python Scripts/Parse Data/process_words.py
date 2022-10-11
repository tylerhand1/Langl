# For the words in extra words ending in 50k.txt
# Author: Hermit Dave
# License: CC-by-sa-4.0
# Link to License: https://github.com/hermitdave/FrequencyWords/blob/master/LICENSE

# For the words in extra words titled german.txt
# Source: http://frequencylists.blogspot.com/2016/01/the-2980-most-frequently-used-german.html

import sys
import pandas as pd

def parse_file(filename):
    lang_set = set()
    counter = 0
    valid_words = ['a', 'à', 'y', 'а', 'в', 'ж', 'и', 'к', 'о', 'с', 'у', 'я']
    valid_letters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
    valid_letters_de = ['ä', 'ö', 'ü', 'ß']
    valid_letters_fr = ['à', 'â', 'æ', 'ç', 'é', 'è', 'ê', 'ë', 'î', 'ï', 'ô', 'œ',
                    'ù', 'û', 'ü', 'ÿ']
    valid_letters_it = ['à', 'ò', 'ó', 'è', 'é', 'ù', 'ú', 'ì', 'í']
    valid_letters_cs = ['á', 'č', 'ď', 'é', 'ě', 'ň', 'ó', 'ř', 'š', 'ť', 'ú', 'ů', 'ý', 'ž']
    valid_letters_ru = ['а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м',
                    'н', 'о' ,'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы',
                    'ь', 'э', 'ю', 'я']
    langs = ['de', 'fr', 'ru']
    if filename in langs:
        with open('Words/{}_30K.txt'.format(filename), 'r') as f:
            lines = f.readlines()
            for line in lines:
                line = line.split()[1]
                has_number = any(i.isdigit() for i in line)
                if not has_number and line.isalpha():
                    if len(line) > 1:
                        line = line.upper()
                        lang_set.add(line)
                    else:
                        if line in valid_words:
                            line = line.upper()
                            lang_set.add(line)
    if filename == 'de':
        with open('Extra Words/german.txt') as f:
            lines = f.readlines()
            for line in lines:
                lang_set.add(line)
    with open('Extra Words/{}_50k.txt'.format(filename)) as f:
        lines = f.readlines()
        if filename == 'de':
            for line in lines:
                line = line.split()[0].lower()
                if not any(char not in valid_letters and char not in valid_letters_de for char in line):
                    line = line.upper()
                    lang_set.add(line)
        if filename == 'fr':
            for line in lines:
                line = line.split()[0].lower()
                if not any(char not in valid_letters and char not in valid_letters_fr for char in line):
                    line = line.upper()
                    lang_set.add(line)
        if filename == 'it':
            for line in lines:
                line = line.split()[0].lower()
                if not any(char not in valid_letters and char not in valid_letters_it for char in line):
                    line = line.upper()
                    lang_set.add(line)
        if filename == 'cs':
            for line in lines:
                line = line.split()[0].lower()
                if not any(char not in valid_letters or char not in valid_letters_cs for char in line):
                    line = line.upper()
                    lang_set.add(line)
        if filename == 'ru':
            for line in lines:
                line = line.split()[0].lower()
                if not any(char not in valid_letters_ru for char in line):
                    line = line.upper()
                    lang_set.add(line)

    with open('Parsed_Words/{}_30K.txt'.format(filename), 'w') as f:
        f.write('ID, WORD\n')
        for line in lang_set:
            f.write('{},{}\n'.format(str(counter), line))
            counter += 1


if __name__ == '__main__':
    parse_file(sys.argv[1])

    read_file = pd.read_csv('Parsed_Words/{}_30K.txt'.format(sys.argv[1]))
    read_file.to_csv('Processed/{}_WORD_TABLE.csv'.format(sys.argv[1].upper()), index=None)