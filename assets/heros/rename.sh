#!/bin/bash

find . -type f -name '*#*' | while IFS= read -r f; do
  dir=$(dirname "$f")
  file=$(basename "$f")

  base="${file% \#*}"
  ext="${file##*.}"

  # Reconstruct new filename
  if [[ "$file" == *.* ]]; then
    newname="${base}.${ext}"
  else
    newname="$base"
  fi

  # Only rename if names differ
  if [[ "$file" != "$newname" ]]; then
    echo "Renaming: '$file' → '$newname'"
    mv -- "$f" "$dir/$newname"
  fi
done

