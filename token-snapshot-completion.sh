#!/usr/bin/env bash
# Command completion for token-snapshot
# Generated by Clikt

__skip_opt_eq() {
    # this takes advantage of the fact that bash functions can write to local
    # variables in their callers
    (( i = i + 1 ))
    if [[ "${COMP_WORDS[$i]}" == '=' ]]; then
          (( i = i + 1 ))
    fi
}

_token_snapshot() {
  local i=1
  local in_param=''
  local fixed_arg_names=()
  local vararg_name=''
  local can_parse_options=1

  while [[ ${i} -lt $COMP_CWORD ]]; do
    if [[ ${can_parse_options} -eq 1 ]]; then
      case "${COMP_WORDS[$i]}" in
        --)
          can_parse_options=0
          (( i = i + 1 ));
          continue
          ;;
        -n|--node-address)
          __skip_opt_eq
          (( i = i + 1 ))
          [[ ${i} -gt COMP_CWORD ]] && in_param='--node-address' || in_param=''
          continue
          ;;
        -c|--contract)
          __skip_opt_eq
          (( i = i + 1 ))
          [[ ${i} -gt COMP_CWORD ]] && in_param='--contract' || in_param=''
          continue
          ;;
        -f|--from)
          __skip_opt_eq
          (( i = i + 1 ))
          [[ ${i} -gt COMP_CWORD ]] && in_param='--from' || in_param=''
          continue
          ;;
        -t|--to)
          __skip_opt_eq
          (( i = i + 1 ))
          [[ ${i} -gt COMP_CWORD ]] && in_param='--to' || in_param=''
          continue
          ;;
        -o|--output)
          __skip_opt_eq
          (( i = i + 1 ))
          [[ ${i} -gt COMP_CWORD ]] && in_param='--output' || in_param=''
          continue
          ;;
        -m|--mode)
          __skip_opt_eq
          (( i = i + 1 ))
          [[ ${i} -gt COMP_CWORD ]] && in_param='--mode' || in_param=''
          continue
          ;;
        -h|--help)
          __skip_opt_eq
          in_param=''
          continue
          ;;
      esac
    fi
    case "${COMP_WORDS[$i]}" in
      *)
        (( i = i + 1 ))
        # drop the head of the array
        fixed_arg_names=("${fixed_arg_names[@]:1}")
        ;;
    esac
  done
  local word="${COMP_WORDS[$COMP_CWORD]}"
  if [[ "${word}" =~ ^[-] ]]; then
    COMPREPLY=($(compgen -W '-n --node-address -c --contract -f --from -t --to -o --output -m --mode -h --help' -- "${word}"))
    return
  fi

  # We're either at an option's value, or the first remaining fixed size
  # arg, or the vararg if there are no fixed args left
  [[ -z "${in_param}" ]] && in_param=${fixed_arg_names[0]}
  [[ -z "${in_param}" ]] && in_param=${vararg_name}

  case "${in_param}" in
    --node-address)
      ;;
    --contract)
      ;;
    --from)
      ;;
    --to)
      ;;
    --output)
      ;;
    --mode)
      ;;
    --help)
      ;;
  esac
}

complete -F _token_snapshot token-snapshot ./token-snapshot
