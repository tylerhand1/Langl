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
    punctuation = ['.', ',', '!', '?', '„', '“', '«', '»', '-', '–', '−' ,'‚' ,'‘' ,'₃' ,'ʰ' ,'×']
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
        for line in lines:
            if not any(char in punctuation and char.isnumeric() for char in line):
                line = line.split()[0].upper()
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