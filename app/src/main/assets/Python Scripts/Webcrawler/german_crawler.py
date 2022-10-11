# BeautifulSoup
from bs4 import BeautifulSoup as bs4
import requests

html_txt = requests.get('http://frequencylists.blogspot.com/2016/01/the-2980-most-frequently-used-german.html').text
soup = bs4(html_txt, 'lxml')
spans = soup.find_all('span')

words = set()

for span in spans:
    if len(span.text.split()) == 8:
        words.add(span.text.split()[4].upper())
        words.add(span.text.split()[7].upper())

with open('../Parse Data/Extra Words/german.txt', 'w') as f:
    for word in words:
        f.write(word + '\n')