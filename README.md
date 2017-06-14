# project 

## spec.tarの解凍方法 
`tar xf spec.tar`

## 仕様
`/before` 内で `./gg` を実行して `after` と同様のディレクリの構造にする。
  
## 確認方法 
`diff -r before after && echo "True" || echo "False"` で `True` が表示されればOK。
